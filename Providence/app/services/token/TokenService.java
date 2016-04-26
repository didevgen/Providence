package services.token;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.jpa.impl.JPADeleteClause;
import models.auth.QTokenImpl;
import models.auth.TokenIdentity;
import models.auth.TokenImpl;
import play.libs.F;
import services.base.DB;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenService {

    protected final static transient Logger logger = Logger.getLogger(TokenService.class.getName());
    private static TokenService instance;
    private final ObjectMapper objectMapper;

    protected TokenService() {
        objectMapper = new ObjectMapper();
    }

    public static TokenService getInstance() {
        TokenService localInstance = instance;
        if (localInstance == null) {
            synchronized (TokenService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TokenService();
                }
            }
        }

        return localInstance;
    }

    public void save(TokenImpl token) {
        if (token == null) {
            return;
        }

        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        try {
            String user = objectMapper.writeValueAsString(token.getUser());
            token.setUserAsString(user);
            DB.save(token);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    private void runInThread(final F.Tuple<String, String> userData,
                             F.Function<F.Tuple<String, String>, Void> function) {
        try {
            //TODO in future add thread pool (if need)
            function.apply(userData);
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, throwable.getMessage(), throwable);
        }
    }
    private TokenImpl getToken(String key) {
        return DB.query().from(QTokenImpl.tokenImpl)
                .where(QTokenImpl.tokenImpl.key.eq(key))
                .singleResult(QTokenImpl.tokenImpl);
    }
    public TokenImpl find(String key) {
        TokenImpl token = getToken(key);
        if (token != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig()
                    .getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
            try {
                TokenIdentity userInfo = objectMapper
                        .readValue(token.getUserAsString(),
                                TokenIdentity.class);
                token.setUser(userInfo);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        return token;
    }

    public void delete(TokenImpl token) {
        if (token == null) {
            return;
        }
        delete(token.getUuid());
    }
    private void delete(String uuid) {
        new JPADeleteClause(DB.getEM(), QTokenImpl.tokenImpl)
                .where(QTokenImpl.tokenImpl.uuid.eq(uuid)).execute();
    }
}

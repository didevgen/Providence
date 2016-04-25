package services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.auth.TokenIdentity;
import controllers.auth.TokenImpl;
import play.libs.F;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Smeyan on 04.12.2015.
 */
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
            String tokenAsString = objectMapper.writeValueAsString(token);
//            RedisService.getInstance().set(token.getKey(), tokenAsString);
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

    public TokenImpl find(String key) {
//        String tokenStr = RedisService.getInstance().get(key);
        String tokenStr = "";

        TokenImpl token = null;
        if (tokenStr != null && !tokenStr.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig()
                    .getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

            try {
                token = objectMapper.readValue(tokenStr, TokenImpl.class);
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
//        RedisService.getInstance().del(token.getKey());
    }

}

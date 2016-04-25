package controllers.auth;

import models.user.User;
import services.TokenService;
import utils.CryptoHelper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class TokenProvider {

    private static final long EXPIRE_TIME = 10;
    private static final Random RANDOM = new Random();


    public static TokenImpl generate(User user) {
        return generate(user, EXPIRE_TIME);
    }

    public static TokenImpl generate(User user, long expire) {
        TokenImpl token = new TokenImpl();
        TokenIdentity tokenIdentity = TokenIdentity.from(user);
        token.setKey(CryptoHelper.getDefaultToken());
//        token.setExpired(DH.nowWithDays(expire));
//        token.setUpdated(DH.now());
        token.setUser(tokenIdentity);
        return token;
    }

    public static boolean isValid(TokenImpl token) {
        if (token == null || token.getUser() == null) {
            return false;
        }
//        if (!DH.now().before(token.getExpired())) {
//            return false;
//        }
        return true;
    }

    public static TokenImpl refreshToken(TokenImpl oldToken, User updatedUser) {
        TokenService.getInstance().delete(oldToken);
        return generate(updatedUser);
    }

}

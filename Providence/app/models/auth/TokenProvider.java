package models.auth;

import models.user.User;
import org.joda.time.DateTime;
import services.token.TokenService;
import utils.CryptoHelper;

import java.util.Random;

public class TokenProvider {

    private static final int EXPIRE_TIME = 10;
    public static TokenImpl generate(User user) {
        return generate(user, EXPIRE_TIME);
    }

    public static TokenImpl generate(User user, long expire) {
        TokenImpl token = new TokenImpl();
        TokenIdentity tokenIdentity = TokenIdentity.from(user);
        token.setKey(CryptoHelper.getDefaultToken());
        token.setExpired(new DateTime().plusDays(EXPIRE_TIME));
        token.setUpdated(DateTime.now());
        token.setUser(tokenIdentity);
        return token;
    }

    public static boolean isValid(TokenImpl token) {
        if (token == null || token.getUser() == null) {
            return false;
        }
        if (!DateTime.now().isBefore(token.getExpired())) {
            return false;
        }
        return true;
    }

    public static TokenImpl refreshToken(TokenImpl oldToken, User updatedUser) {
        TokenService.getInstance().delete(oldToken);
        return generate(updatedUser);
    }

}

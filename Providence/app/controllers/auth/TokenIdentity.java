package controllers.auth;

import models.user.User;

/**
 * Created by Smeyan on 04.12.2015.
 */
public class TokenIdentity {

    private String id;
    private String login;

    public TokenIdentity() {
    }

    public TokenIdentity(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public static TokenIdentity from(User user) {
//        TokenIdentity tokenIdentity = new TokenIdentity(user.getUuid(), user.getBaseUser().getEmail());
//        return tokenIdentity;
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

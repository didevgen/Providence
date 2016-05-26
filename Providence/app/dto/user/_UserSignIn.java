package dto.user;

import models.user.User;

/**
 * Created by Eugne on 26.05.2016.
 */
public class _UserSignIn {

    private String login;

    private String password;

    public _UserSignIn toDto(User object) {
        this.setLogin(object.getLogin());
        this.setPassword(object.getPassword());
        return this;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

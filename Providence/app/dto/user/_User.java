package dto.user;

import models.user.User;

/**
 * Created by Eugne on 26.05.2016.
 */
public class _User {
    private String uuid;
    private String login;

    public _User toDto(User object) {
        this.setUuid(object.getUuid());
        this.setLogin(object.getLogin());
        return this;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

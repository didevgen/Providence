package models.auth;

import models.base.IndexEntity;
import models.user.User;

import javax.persistence.*;


@Entity
@Table(name="token_identity")
public class TokenIdentity extends IndexEntity{

    @Column(name="login")
    private String login;

    @OneToOne
    @PrimaryKeyJoinColumn
    private TokenImpl token;

    public TokenIdentity() {
    }

    public TokenIdentity(String id, String login) {
        setId(Long.valueOf(id));
        this.login = login;
    }

    public static TokenIdentity from(User user) {
        TokenIdentity tokenIdentity = new TokenIdentity(user.getUuid(), user.getLogin());
        return tokenIdentity;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public TokenImpl getToken() {
        return token;
    }

    public void setToken(TokenImpl token) {
        this.token = token;
    }
}

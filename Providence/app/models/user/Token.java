package models.user;

import models.base.IndexEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tokens")
public class Token extends IndexEntity{

    @Column(name="auth_key")
    private String authKey;

    @Column(name="expiration")
    private Date expired;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public Token() {
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

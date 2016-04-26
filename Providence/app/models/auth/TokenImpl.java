package models.auth;

import models.base.IndexEntity;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class TokenImpl extends IndexEntity implements IToken {

    @Column(name ="token_key")
    private String key;

    @Column(name="token_expired")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime expired;

    @Column(name="last_updated")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime updated;

    @OneToOne(optional=false, mappedBy="token" , cascade = CascadeType.ALL)
    private TokenIdentity user;

    @Column(name ="string_user")
    private String userAsString;

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public DateTime getExpired() {
        return expired;
    }

    @Override
    public DateTime getUpdatedDate() {
        return updated;
    }

    public void setExpired(DateTime expired) {
        this.expired = expired;
    }

    public void setUpdated(DateTime updated) {
        this.updated = updated;
    }

    public TokenIdentity getUser() {
        return user;
    }

    public void setUser(TokenIdentity user) {
        this.user = user;
    }

    public String getUserAsString() {
        return userAsString;
    }

    public void setUserAsString(String userAsString) {
        this.userAsString = userAsString;
    }

    public DateTime getUpdated() {
        return updated;
    }
}

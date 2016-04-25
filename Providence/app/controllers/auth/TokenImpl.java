package controllers.auth;

import java.util.Date;

/**
 * Created by Smeyan on 04.12.2015.
 */
public class TokenImpl implements IToken {

    private String key;
    private Date expired;
    private Date updated;
    private TokenIdentity user;
    private String userAsString;

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Date getExpired() {
        return expired;
    }

    @Override
    public Date getUpdatedDate() {
        return updated;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public void setUpdated(Date updated) {
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

}

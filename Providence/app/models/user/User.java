package models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.base.IndexEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="user")
public class User extends IndexEntity {

    @Column(name ="login")
    private String login;

    @JsonIgnore
    @Column(name ="password")
    private String password;

    public User() {
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

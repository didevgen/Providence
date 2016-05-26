package models.university;

import models.base.IndexEntity;
import models.history.Transaction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name="person")
public class Person  extends IndexEntity{

    @Column(name="name")
    private String fullName;

    @Column(name="card_id")
    private String cardId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "person")
    private List<Transaction> transactionList;

    public Person() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}

package models.university;

import models.base.IndexEntity;
import models.history.Transaction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name = "auditory")
public class Auditory extends IndexEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "aud_num")
    private Integer number;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditory", fetch = FetchType.LAZY)
    private List<Transaction> transactionList;

    public Auditory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}

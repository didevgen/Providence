package models.history;

import models.base.IndexEntity;
import models.university.Auditory;
import models.university.Person;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;


@Entity
@Table(name="transaction")
public class Transaction extends IndexEntity{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Person person;

    @Column(name="pin")
    private int pin;

    @Column(name="verified")
    private int verified;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Auditory auditory;

    @Column(name="eventType")
    private int eventType;

    @Column(name="in_out")
    private int inOutState;

    @Column(name="time")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime time;

    public Transaction() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getInOutState() {
        return inOutState;
    }

    public void setInOutState(int inOutState) {
        this.inOutState = inOutState;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}

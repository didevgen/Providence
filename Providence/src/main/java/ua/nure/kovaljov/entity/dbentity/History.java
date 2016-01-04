package ua.nure.kovaljov.entity.dbentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "transaction")
public class History {

	private long transactionId;
	
	private VerifyMode verifyMode;
	
	private EventType eventType;

	private Room room;
	
	private long inOutState;
	
	private Date time;
	
	private long cardId;
	
	
	private Person person;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "transaction_id")
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "verify_id")
	public VerifyMode getVerifyMode() {
		return verifyMode;
	}

	public void setVerifyMode(VerifyMode verifyMode) {
		this.verifyMode = verifyMode;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "event_id")
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	@Column(name = "inOutState")
	public long getInOutState() {
		return inOutState;
	}

	public void setInOutState(long inOutState) {
		this.inOutState = inOutState;
	}
	@Column(name = "time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	@Column(name = "card_id")
	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	
	@Transient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "room_id")
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
}

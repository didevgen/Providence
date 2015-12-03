package ua.nure.kovaljov.entity;

public class Transaction {
	private long cardId;

	private int pin;

	private int verified;

	private int doorId;

	private int eventType;

	private int inOutState;

	private long time;
	
	public Transaction() {
		super();
	}

	public Transaction(long cardId, int pin, int verified, 
			int doorId, int eventType, int inOutState, long time) {
		super();
		this.cardId = cardId;
		this.pin = pin;
		this.verified = verified;
		this.doorId = doorId;
		this.eventType = eventType;
		this.inOutState = inOutState;
		this.time = time;
	}

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
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

	public int getDoorId() {
		return doorId;
	}

	public void setDoorId(int doorId) {
		this.doorId = doorId;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Transaction [cardId=" + cardId + ", pin=" + pin + ", verified=" + verified + ", doorId=" + doorId
				+ ", eventType=" + eventType + ", inOutState=" + inOutState + ", time=" + time + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cardId ^ (cardId >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (cardId != other.cardId)
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	
	

}

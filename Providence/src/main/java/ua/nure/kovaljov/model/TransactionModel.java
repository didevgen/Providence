package ua.nure.kovaljov.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.entity.Transaction;

public class TransactionModel {

	private long cardId;

	private int verifiedId;

	private String doorId;

	private int inOutState;

	private Date time;
	
	private int eventId;
	

	public TransactionModel() {
	}

	public TransactionModel(long cardId, int verifCode, String doorId, int eventId, int inOutState, String time) {
		this.cardId = cardId;
		this.doorId = doorId;
		this.verifiedId = verifCode;
		this.inOutState = inOutState;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		try {
			this.time = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.eventId = eventId;
	}

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}


	public int getVerifiedId() {
		return verifiedId;
	}

	public void setVerifiedId(int verifiedId) {
		this.verifiedId = verifiedId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public int getInOutState() {
		return inOutState;
	}

	public void setInOutState(int inOutState) {
		this.inOutState = inOutState;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


	public static List<TransactionModel> getModelFromTransaction(Transaction[] transaction) {
		List<TransactionModel> model = new ArrayList<TransactionModel>();
		for (Transaction tr : transaction) {
			model.add(new TransactionModel(tr.getCardId(), 
					tr.getVerified(), tr.getDoorId(),
					tr.getEventType(), tr.getInOutState(), 
					tr.getTime()));
		}
		return model;
	}

	@Override
	public String toString() {
		return "TransactionModel [cardId=" + cardId + ", time=" + time + "]";
	}

}

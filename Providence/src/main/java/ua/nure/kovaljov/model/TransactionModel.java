package ua.nure.kovaljov.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.kovaljov.entity.Transaction;

public class TransactionModel {

	private long cardId;

	private int verifiedId;

	private int doorId;

	private int inOutState;

	private Date time;
	
	private int eventId;
	

	public TransactionModel() {
	}

	public TransactionModel(long cardId, int verifCode, int doorId, int eventId, int inOutState, String time) {
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

	private static final Map<Integer, String> verifModeToCode = new HashMap<Integer, String>();
	private static final Map<Integer, String> eventTypeMap = new HashMap<Integer, String>();

	static {
		verifModeToCode.put(1, "Only finger");
		verifModeToCode.put(3, "Only password");
		verifModeToCode.put(4, "Only card");
		verifModeToCode.put(11, " Card and password");
		verifModeToCode.put(16, "Others");
		
		
		eventTypeMap.put(0, "Punch normally");
		eventTypeMap.put(1, "Punch in the normal-open time segment");
		eventTypeMap.put(2, "First-card normal");
		eventTypeMap.put(3, "Multi-card ");
		eventTypeMap.put(4, "Emergency password");
		eventTypeMap.put(5, "Open the door in the normal-open time segment");
		eventTypeMap.put(6, "Trigger the linkage event");
		eventTypeMap.put(7, "Remote control event ");
		eventTypeMap.put(20, "The punch interval is extremely short");
		eventTypeMap.put(21, "Punch in a non-effective time segment");
		eventTypeMap.put(22, "Illegal time zone");
		eventTypeMap.put(23, "Illegal access");
		eventTypeMap.put(24, "Anti-passback");
		eventTypeMap.put(25, "Fail to interlock");
		eventTypeMap.put(26, "Authenticate by multiple cards");
		eventTypeMap.put(27, "The card is not registered");
		eventTypeMap.put(28, "The opening state times out");
		eventTypeMap.put(29, "The card has expired");
		eventTypeMap.put(101, "Open the door by the duress password");
		eventTypeMap.put(102, "Open the door accidentally");
		eventTypeMap.put(200, "The door has been opened");
		eventTypeMap.put(201, "The door has been closed");
		eventTypeMap.put(202, "Open the door by a button upon leaving");
		eventTypeMap.put(220, "The auxiliary input point is off");
		eventTypeMap.put(221, "The auxiliary input point is short-circuited");
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

	public int getDoorId() {
		return doorId;
	}

	public void setDoorId(int doorId) {
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

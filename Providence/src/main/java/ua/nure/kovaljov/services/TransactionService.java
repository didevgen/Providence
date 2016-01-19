package ua.nure.kovaljov.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.nure.kovaljov.database.dao.impl.TransactionDAOImpl;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.entity.dbentity.UserSession;
import ua.nure.kovaljov.model.TransactionModel;

public class TransactionService {
	public Date getLastDate() {
		return new TransactionDAOImpl().getLatestDate();
	}

	public void insertTransactionModels(List<TransactionModel> models) {
		new TransactionDAOImpl().insertTransactions(models);
	}

	public List<History> getAllHistory() {
		return new TransactionDAOImpl().getAllHistory();
	}

	public List<History> getLastMonthHistory() {
		return new TransactionDAOImpl().getMonthHistory();
	}

	public List<History> getUserHistory(long cardNumber) {
		return new TransactionDAOImpl().getHistoryByCardNumber(cardNumber);
	}

	public List<History> getLatestHistory(Date date) {
		return new TransactionDAOImpl().getHistoryGreaterThanDate(date);
	}

	private Set<Person> getPeople(List<History> history) {
		Set<Person> people = new HashSet<>();
		for (History h : history) {
			people.add(h.getPerson());
		}
		return people;
	}

	private List<History> getSingleDayHistory(Person person, List<History> hist, Date date) {
		List<History> result = new ArrayList<>();
		for (History h : hist) {
			if (h.getTime().compareTo(date) < 0 && h.getPerson().equals(person)) {
				result.add(h);
			}
		}
		return result;
	}

	private List<UserSession> getPersonSessions(List<History> histories, Person person) {
		List<UserSession> sessions = new ArrayList<>();
		int counter = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(0L));
		for (int i = 0; i < histories.size(); i++) {
			History h = histories.get(i);
			if (cal.getTime().compareTo(h.getTime()) > 0 && h.getPerson().equals(person)) {
				counter = i;
				cal.setTime(histories.get(counter).getTime());
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 59);
			}
		}
		return sessions;
	}

	public List<UserSession> getRoomHistory(int roomId) {
		List<History> history = new TransactionDAOImpl().getHistoryByRoomNumber(roomId);
		List<UserSession> sessions = new ArrayList<>();
		Set<Person> people = getPeople(history);
		for (Person person : people) {
			sessions.addAll(getPersonSessions(history, person));
		}
		return sessions;
	}
}

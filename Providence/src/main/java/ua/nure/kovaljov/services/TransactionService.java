package ua.nure.kovaljov.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

	private List<History> sortListByDates(List<History> histories) {
		Collections.sort(histories, new Comparator<History>() {

			@Override
			public int compare(History o1, History o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
		});
		return histories;
	}

	private Calendar setMaxDateTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar;
	}
	
	private Calendar setMinDate(Calendar calendar) {
		Calendar minDate = Calendar.getInstance();
		minDate.setTime(calendar.getTime());
		minDate.set(Calendar.HOUR_OF_DAY, 0);
		minDate.set(Calendar.MINUTE, 0);
		minDate.set(Calendar.SECOND, 0);
		return minDate;
	}

	private Date setMaxDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal = setMaxDateTime(cal);
		return cal.getTime();
	}

	private List<History> findAllDailyEntrances(Person person, List<History> histories, Calendar maxDate) {
		List<History> entrances = new ArrayList<>();
		
		Calendar minDate = setMinDate(maxDate);
		for (History h : histories) {
			if (h.getTime().compareTo(maxDate.getTime()) > 0) {
				return entrances;
			}
			if (h.getPerson().equals(person) && h.getRoom().getDoorState().equals("entrance") && h.getTime().compareTo(minDate.getTime()) > 0 ) {
				entrances.add(h);
			}
		}
		return entrances;
	}

	private List<History> findAllDailyExits(Person person, List<History> histories, Calendar maxDate) {
		List<History> entrances = new ArrayList<>();
		Calendar minDate = setMinDate(maxDate);
		for (History h : histories) {
			if (h.getTime().compareTo(maxDate.getTime()) > 0) {
				return entrances;
			}
			if (h.getPerson().equals(person) && h.getRoom().getDoorState().equals("exit") && h.getTime().compareTo(minDate.getTime()) > 0) {
				entrances.add(h);
			}
		}
		return entrances;
	}

	private UserSession getUserSession(History entrance, List<History> exits,Person person) {
		UserSession session = new UserSession();
		session.setStartDate(entrance.getTime());
		session.setPerson(person);
		for (History h : exits) {
			if (entrance.getTime().compareTo(h.getTime()) < 0) {
				session.setFinishDate(h.getTime());
				return session;
			}
		}
		session.setFinishDate(setMaxDateTime(entrance.getTime()));
		return session;
	}

	private List<UserSession> getSessions(List<History> entrances, List<History> exits, Person person) {
		List<UserSession> sessions = new ArrayList<>();
		Set<UserSession> result = new LinkedHashSet<>();
		for (History h : entrances) {
			result.add(getUserSession(h, exits,person));
		}
		sessions.addAll(result);
		return sessions;
	}

	private List<UserSession> findUserDailySession(Person person, List<History> histories, Calendar maxDate) {
		List<UserSession> sessions = new ArrayList<>();
		List<History> entrances = findAllDailyEntrances(person, histories, maxDate);
		List<History> exits = findAllDailyExits(person, histories, maxDate);
		sessions = getSessions(entrances, exits,person);
		return sessions;
	}

	private List<UserSession> getPersonSessions(List<History> histories, Person person) {
		List<UserSession> sessions = new ArrayList<>();
		if (histories.isEmpty()) {
			return sessions;
		}
		histories = sortListByDates(histories);
		Calendar startSearch = Calendar.getInstance();
		startSearch.setTime(histories.get(0).getTime());
		Calendar finishSearch = Calendar.getInstance();
		finishSearch.setTime(histories.get(histories.size() - 1).getTime());
		startSearch = setMaxDateTime(startSearch);
		finishSearch = setMaxDateTime(finishSearch);
		while (startSearch.compareTo(finishSearch) <= 0) {
			sessions.addAll(findUserDailySession(person, histories, startSearch));
			startSearch.add(Calendar.DATE, 1);
		}
		return sessions;
	}

	public List<UserSession> getRoomHistory(String roomName) {
		List<History> history = new TransactionDAOImpl().getHistoryByRoomName(roomName);
		List<UserSession> sessions = new ArrayList<>();
		Set<Person> people = getPeople(history);
		for (Person person : people) {
			sessions.addAll(getPersonSessions(history, person));
		}
		return sessions;
	}
}

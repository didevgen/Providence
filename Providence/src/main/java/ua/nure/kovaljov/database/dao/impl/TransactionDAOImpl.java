package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.TransactionDAO;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.entity.dbentity.Room;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.model.TransactionModel;
import ua.nure.kovaljov.utils.HibernateUtil;

public class TransactionDAOImpl extends BaseCRUD implements TransactionDAO {

	@Override
	public Date getLatestDate() {
		Session session = null;
		Date date = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			date = (Date) session.createSQLQuery("Select Max(transaction.time) from transaction ").list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return date;
	}

	@Override
	public void insertTransactions(List<TransactionModel> transactions) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			for (TransactionModel model : transactions) {
				Room room = new Room();
				room.setDoorState("entrance");
				room.setRoomIp(model.getDoorId());
				room.setRoomName("no name");
				Room r = new RoomDAOImpl().insertRoom(room);
				SQLQuery insertQuery1 = session.createSQLQuery(
						"INSERT INTO transaction (`verify_id`,`room_id`,`event_id`,`inOutState`,`time`,`card_id`)"
								+ " VALUES(?,?,?,?,?,?);");
				insertQuery1.setParameter(0, model.getVerifiedId());
				insertQuery1.setParameter(1, r.getRoomId());
				insertQuery1.setParameter(2, model.getEventId());
				insertQuery1.setParameter(3, model.getInOutState());
				insertQuery1.setParameter(4, model.getTime());
				insertQuery1.setLong(5, model.getCardId());
				insertQuery1.executeUpdate();
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	private Person insertUnregisteredPerson(long cardId) {
		Person person = new Person();
		person.setCardNumber(cardId);
		person.setPersonName("Unregistered");
		person.setEmail("");
		person = new PersonDAOImpl().insertPerson(person);
		return person;
	}

	private Person getPersonByCard(long cardId) {
		Session session = null;
		Person person = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(Person.class);
			cr.add(Restrictions.eq("cardNumber", cardId));
			@SuppressWarnings("unchecked")
			List<Person> lst = cr.list();
			for (Person p : lst) {
				Hibernate.initialize(p.getSubscribers());
				for (User user : p.getSubscribers()) {
					user.setSubsribedPersons(null);
				}
			}
			if (lst.size() == 0) {
				session.clear();
				if (new PersonDAOImpl().getPersonByCardNumber(cardId) == null) {
					return insertUnregisteredPerson(cardId);
				}
			} else {
				return lst.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return person;
	}

	private Person getPersonByCard(List<Person> persons, long cardId) {
		for (Person person : persons) {
			if (person.getCardNumber() == cardId) {
				return person;
			}
		}
		return null;
	}

	@Override
	public List<History> getAllHistory() {
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<>();
		List<Object> obj = super.getAllObjects(History.class);
		for (Object o : obj) {
			objects.add((History) o);
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getMonthHistory() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(History.class).add(Restrictions.ge("time", c.getTime()))
					.add(Restrictions.ne("cardId", 0L)).addOrder(Order.asc("time")).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	public List<History> getTodayHistory(String roomName) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(History.class).add(Restrictions.ge("time", c.getTime()))
					.add(Restrictions.ne("cardId", 0L)).addOrder(Order.asc("time")).list();
			Iterator<History> iter = objects.iterator();
			while (iter.hasNext()) {
				History object = iter.next();
				if (!object.getRoom().getRoomName().equalsIgnoreCase(roomName)) {
					iter.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistoryByCardNumber(long cardNumber) {
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(History.class).add(Restrictions.eq("cardId", cardNumber))
					.addOrder(Order.asc("time")).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistoryGreaterThanDate(Date date) {
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(History.class).add(Restrictions.ge("time", date))
					.add(Restrictions.ne("cardId", 0L)).list();
			for (History h : history) {
				Hibernate.initialize(h.getRoom());
				Hibernate.initialize(h.getRoom().getSubscribers());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	private void fillWithPersons(List<History> objects, List<Person> cardNumbers, List<History> history) {
		for (History h : objects) {
			if (h.getCardId() == 0) {
				continue;
			}
			Person person = getPersonByCard(cardNumbers, h.getCardId());
			if (person == null) {
				person = getPersonByCard(h.getCardId());
				cardNumbers.add(person);
			}
			person.setGroups(null);
			if (!person.getPersonName().equals("Unregistered")) {
				h.setPerson(person);
				history.add(h);
			}
		}
	}

	public List<History> getHistoryByRoomNumber(int roomId) {
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(History.class).add(Restrictions.eq("room.roomId", roomId))
					.addOrder(Order.asc("time")).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}

	public List<History> getHistoryByRoomName(String roomName) {
		List<Room> appliableRooms = new RoomDAOImpl().getRoomByName(roomName);
		Session session = null;
		List<Person> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<History>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			for (Room room : appliableRooms) {
				objects.addAll(session.createCriteria(History.class)
						.add(Restrictions.eq("room.roomId", room.getRoomId())).addOrder(Order.asc("time")).list());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		fillWithPersons(objects, cardNumbers, history);
		return history;
	}
}

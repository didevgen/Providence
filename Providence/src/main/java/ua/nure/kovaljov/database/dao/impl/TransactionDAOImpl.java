package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.TransactionDAO;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.entity.dbentity.Person;
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
				SQLQuery insertQuery = session.createSQLQuery(
						"INSERT IGNORE INTO `room` (`room_ip`,`room_name`,`door_state`) VALUES (?,'no name','entrance');");
				insertQuery.setParameter(0, model.getDoorId());
				insertQuery.executeUpdate();
				SQLQuery insertQuery1 = session.createSQLQuery("INSERT INTO transaction (`verify_id`,`room_ip`,`event_id`,`inOutState`,`time`,`card_id`)"
				+ " VALUES(?,?,?,?,?,?);");
				insertQuery1.setParameter(0, model.getVerifiedId());
				insertQuery1.setParameter(1, model.getDoorId());
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
			if (lst.size() == 0) {
				return insertUnregisteredPerson(cardId);
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
		for (Object o: obj) {
			objects.add((History)o);
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
					.add(Restrictions.ne("cardId", 0L)).list();
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
			objects = session.createCriteria(History.class).add(Restrictions.eq("cardId", cardNumber)).list();
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
			h.setPerson(person);
			history.add(h);
		}
	}
}

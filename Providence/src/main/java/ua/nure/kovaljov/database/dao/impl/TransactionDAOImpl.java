package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.TransactionDAO;
import ua.nure.kovaljov.entity.dbentity.History;
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
				SQLQuery insertQuery = session.createSQLQuery(
						"INSERT INTO transaction(`verify_id`,`door_id`,`event_id`,`inOutState`,`time`,card_id)"
								+ "VALUES(?,?,?,?,?,?);");
				insertQuery.setParameter(0, model.getVerifiedId());
				insertQuery.setParameter(1, model.getDoorId());
				insertQuery.setParameter(2, model.getEventId());
				insertQuery.setParameter(3, model.getInOutState());
				insertQuery.setParameter(4, model.getTime());
				insertQuery.setLong(5, model.getCardId());
				insertQuery.executeUpdate();
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

	private User insertUnregisteredUser(long cardId) {
		User user = new User();
		user.setCardNumber(cardId);
		user.setUserName("Unregistered");
		user.setEmail("");
		user = new UserDAOImpl().insertUser(user);
		return user;
	}

	private User getUserByCard(long cardId) {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("cardNumber", cardId));
			@SuppressWarnings("unchecked")
			List<User> lst = cr.list();
			if (lst.size() == 0) {
				return insertUnregisteredUser(cardId);
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
		return user;
	}

	private User getUserByCard(List<User> users, long cardId) {
		for (User user : users) {
			if (user.getCardNumber() == cardId) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<History> getAllHistory() {
		List<User> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<History> objects = new ArrayList<>();
		List<Object> obj = super.getAllObjects(History.class);
		for (Object o: obj) {
			objects.add((History)o);
		}
		fillWithUsers(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getMonthHistory() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Session session = null;
		List<User> cardNumbers = new ArrayList<>();
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
		fillWithUsers(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistoryByCardNumber(long cardNumber) {
		Session session = null;
		List<User> cardNumbers = new ArrayList<>();
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
		fillWithUsers(objects, cardNumbers, history);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistoryGreaterThanDate(Date date) {
		Session session = null;
		List<User> cardNumbers = new ArrayList<>();
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
		fillWithUsers(objects, cardNumbers, history);
		return history;
	}

	private void fillWithUsers(List<History> objects, List<User> cardNumbers, List<History> history) {
		for (History h : objects) {
			if (h.getCardId() == 0) {
				continue;
			}
			User user = getUserByCard(cardNumbers, h.getCardId());
			if (user == null) {
				user = getUserByCard(h.getCardId());
				cardNumbers.add(user);
			}
			user.setGroups(null);
			h.setUser(user);
			history.add(h);
		}
	}
}

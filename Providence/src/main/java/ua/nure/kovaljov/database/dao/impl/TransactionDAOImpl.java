package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.GroupDAO;
import ua.nure.kovaljov.database.dao.TransactionDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
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
		long event_id = 0;
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
				event_id = model.getEventId();
				insertQuery.executeUpdate();
				getUserByCard(model.getCardId());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(event_id);
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
			List<User> lst = cr.list();
			if (lst.size()==0) {
				return insertUnregisteredUser(cardId);
			}
			else {
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
		for(User user : users) {
			if (user.getCardNumber()==cardId) {
				return user;
			}
		}
		return null;
	}
	@Override
	public List<History> getAllHistory() {
		List<User> cardNumbers = new ArrayList<>();
		List<History> history = new ArrayList<History>();
		List<Object> objects = super.getAllObjects(History.class);
		for (Object o : objects) {
			History h = (History) o;
			if (h.getCardId()==0) {
				continue;
			}
			User user = getUserByCard(cardNumbers, h.getCardId());
			if (user==null) {
				user = getUserByCard(h.getCardId());
				cardNumbers.add(user);
			}
			user.setGroups(null);
			h.setUser(user);
			history.add(h);
		}
		return history;
	}

}

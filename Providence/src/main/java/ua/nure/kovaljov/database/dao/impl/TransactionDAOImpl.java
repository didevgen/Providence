package ua.nure.kovaljov.database.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.TransactionDAO;
import ua.nure.kovaljov.model.TransactionModel;
import ua.nure.kovaljov.utils.HibernateUtil;

public class TransactionDAOImpl implements TransactionDAO {

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
				SQLQuery insertQuery = session.createSQLQuery
						("INSERT INTO transaction(`verfiy_id`,`door_id`,`event_id`,`inOutState`,`time`)"
								+ "VALUES(?,?,?,?,?);");
				insertQuery.setParameter(0, model.getVerifiedId());
				insertQuery.setParameter(1, model.getDoorId());
				insertQuery.setParameter(2, model.getEventId());
				insertQuery.setParameter(3, model.getInOutState());
				System.out.println(model.getTime());
				insertQuery.setParameter(4, model.getTime());
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

}

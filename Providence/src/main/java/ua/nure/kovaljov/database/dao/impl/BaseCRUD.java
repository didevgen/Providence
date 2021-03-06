package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.CRUD;
import ua.nure.kovaljov.utils.HibernateUtil;

public class BaseCRUD implements CRUD{
	private Logger log = LogManager.getLogger(BaseCRUD.class);
	@Override
	public Object getObject(long objectId,Class<?> clazzName) {
		Session session = null;
		Object clazz = new Object();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			clazz = session.get(clazzName, objectId);
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return clazz;
	}

	@Override
	public Object insertObject(Object obj) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return obj;
	}

	@Override
	public void deleteObject(long objectId, String objectName, String whereClause) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query q = session.createQuery("delete "+objectName+" where "+whereClause+" = " + objectId);
			q.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}		
	}

	@Override
	public Object updateObject(Object anotherObject) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(anotherObject);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return anotherObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllObjects(Class<?> classCriteria) {
		Session session = null;
		List<Object> objects = new ArrayList<Object>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(classCriteria).list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

}

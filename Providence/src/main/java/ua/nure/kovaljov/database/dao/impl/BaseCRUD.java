package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.CRUD;
import ua.nure.kovaljov.utils.HibernateUtil;

public class BaseCRUD implements CRUD{

	@Override
	public Object getObject(long objectId,Class<?> clazzName) {
		Session session = null;
		Object clazz = new Object();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			clazz = session.load(clazzName, objectId);
		} catch (Exception e) {
			e.printStackTrace();
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
			System.out.println(e.getMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return obj;
	}

	@Override
	public void deleteObject(long objectId, String objectName) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query q = session.createQuery("delete "+objectName+" where class_id = " + objectId);
			q.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return anotherObject;
	}

	@Override
	public List<Object> getAllObjects(Class<?> classCriteria) {
		Session session = null;
		List<Object> objects = new ArrayList<Object>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(classCriteria).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

}

package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.utils.HibernateUtil;

public class UserDAOImpl extends BaseCRUD implements UserDAO {
	private Logger log = LogManager.getLogger(UserDAOImpl.class);
	@Override
	public User getUser(long objectId, Class<User> className) {
		Session session = null;
		User user = new User();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			user = session.get(User.class, objectId);
			for (Group group : user.getGroups()) {
				Hibernate.initialize(group);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

	@Override
	public User insertUser(User obj) {
		return (User) super.insertObject(obj);
	}

	@Override
	public void deleteUser(long objectId, String objectName) {
		super.deleteObject(objectId, objectName, "user_id");
	}

	@Override
	public User updateUser(User user) {
		return (User) super.updateObject(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Session session = null;
		List<User> objects = new ArrayList<User>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(User.class).list();
			for (User o : objects) {
				Hibernate.initialize(o.getGroups());
				o.getGroups().forEach(item -> item.setUsers(null));
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

	@Override
	public List<User> userWithoutGroup() {
		return getAllUsers();
	}

	@SuppressWarnings("unchecked")
	public User getUserByCardNumber(long cardNumber) {
		Session session = null;
		List<User> user = new ArrayList<User>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			user = session.createCriteria(User.class).add(Restrictions.eq("cardNumber", cardNumber)).list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user.size() == 0 ? null : user.get(0);
	}

}

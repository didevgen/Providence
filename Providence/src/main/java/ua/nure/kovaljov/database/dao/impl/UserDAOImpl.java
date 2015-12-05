package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.utils.HibernateUtil;

public class UserDAOImpl extends BaseCRUD implements UserDAO{

	@Override
	public User getUser(long objectId, Class<User> className) {
		return (User) super.getObject(objectId, className);
	}

	@Override
	public User insertUser(User obj) {
		return (User) super.insertObject(obj);
	}

	@Override
	public void deleteUser(long objectId, String objectName) {
		super.deleteObject(objectId, objectName,"user_id");
	}

	@Override
	public User updateUser(User user) {
		return (User) super.updateObject(user);
	}

	@Override
	public List<User> getAllUsers() {
		Session session = null;
		List<User> objects = new ArrayList<User>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(User.class).list();
			for(User o : objects) {
				Hibernate.initialize(o.getGroups());
				o.getGroups().forEach(item->item.setUsers(null));
			}
		} catch (Exception e) {
			e.printStackTrace();
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

}

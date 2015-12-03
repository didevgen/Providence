package ua.nure.kovaljov.database.dao.impl;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.entity.dbentity.User;

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
		super.deleteObject(objectId, objectName);
	}

	@Override
	public User updateUser(User user) {
		return (User) super.updateObject(user);
	}

}

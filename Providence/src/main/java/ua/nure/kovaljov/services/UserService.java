package ua.nure.kovaljov.services;

import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.User;

public class UserService {
	public User insertUser(User user) {
		return new UserDAOImpl().insertUser(user);
	}
	public User updateUser(User user) {
		return new UserDAOImpl().updateUser(user);
	}
	public void deleteUser(long userId) {
		new UserDAOImpl().deleteObject(userId, "User");
	}
	public User getUser(long userId) {
		return new UserDAOImpl().getUser(userId, User.class);
	}
} 

package ua.nure.kovaljov.database.dao;

import java.util.List;

import ua.nure.kovaljov.entity.dbentity.User;

public interface UserDAO {
	User getUser(long objectId,Class<User> className);

	User insertUser(User obj);

	void deleteUser(long objectId, String objectName);

	User updateUser(User user);
	
	List<User> getAllUsers();
}

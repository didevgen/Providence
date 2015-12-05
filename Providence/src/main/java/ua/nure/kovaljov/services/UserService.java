package ua.nure.kovaljov.services;

import java.util.ArrayList;
import java.util.List;

import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.User;

public class UserService {
	public User insertUser(User user) {
		return new UserDAOImpl().insertUser(user);
	}
	public User updateUser(User user) {
		user.getGroups().forEach(item->item.setUsers(null));
		return new UserDAOImpl().updateUser(user);
	}
	public void deleteUser(long userId) {
		new UserDAOImpl().deleteUser(userId, "User");
	}
	public User getUser(long userId) {
		return new UserDAOImpl().getUser(userId, User.class);
	}
	public List<User> getUsers() {
		return new UserDAOImpl().getAllUsers();
	}
	
	public List<User> getUsersWithoutGroup(long groupId){
		List<User> users = new UserDAOImpl().getAllUsers();
		Group group = new Group();
		group.setId(groupId);
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (!user.getGroups().contains(group)) {
				result.add(user);
			}
		}
		return result;
	}
	
	public void removeGroupFromUser(long groupId, long userId) {
		List<User> users = new UserDAOImpl().getAllUsers();
		Group group = new Group();
		group.setId(groupId);
		for (User user : users) {
			if (user.getUserId() == userId && user.getGroups().contains(group)) {
				user.getGroups().remove(group);
				new UserDAOImpl().updateUser(user);
				return;
			}
		}
	}
} 

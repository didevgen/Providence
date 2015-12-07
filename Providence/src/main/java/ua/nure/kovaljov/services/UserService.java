package ua.nure.kovaljov.services;

import java.util.ArrayList;
import java.util.List;

import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.websockets.container.WSContainer;

public class UserService {
	public User insertUser(User user) {
		String card = String.valueOf(user.getCardNumber());
		if (card.length() == 7) {
			WSContainer.sendToAllDesktopConnectedSessions("Insert:" + card);
		}
		return new UserDAOImpl().insertUser(user);
	}

	public User updateUser(User user) {
		long cardId = getUser(user.getUserId()).getCardNumber();
		if (cardId != user.getCardNumber()) {
			String card = String.valueOf(cardId);
			if (card.length() == 7) {
				WSContainer.sendToAllDesktopConnectedSessions("Delete:" + card);
			}
			card = String.valueOf(user.getCardNumber());
			if (card.length() == 7) {
				WSContainer.sendToAllDesktopConnectedSessions("Insert:" + card);
			}
		}
		user.getGroups().forEach(item -> item.setUsers(null));
		return new UserDAOImpl().updateUser(user);
	}

	public void deleteUser(long userId) {
		long cardId = getUser(userId).getCardNumber();
		new UserDAOImpl().deleteUser(userId, "User");
		String card = String.valueOf(cardId);
		if (card.length() == 7) {
			WSContainer.sendToAllDesktopConnectedSessions("Delete:" + card);
		}
	}

	public User getUser(long userId) {
		return new UserDAOImpl().getUser(userId, User.class);
	}

	public List<User> getUsers() {
		return new UserDAOImpl().getAllUsers();
	}

	public List<User> getUsersWithoutGroup(long groupId) {
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

package ua.nure.kovaljov.services;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import ua.nure.kovaljov.database.dao.impl.RoomDAOImpl;
import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Room;
import ua.nure.kovaljov.entity.dbentity.User;

public class RoomService {
	public List<Room> getRooms() {
		return new RoomDAOImpl().getAllRooms();
	}

	public void updateRoom(Room room, int id) {
		new RoomDAOImpl().updateRoom(room, id);
	}
	public void addSubscriber(int id, int roomId) {
		new RoomDAOImpl().addSubscriber(id, roomId);
	}
	public void deleteSubscriber(int id, int roomId) {
		new RoomDAOImpl().deleteSubscriber(id, roomId);
	}

	public void deleteRoom(Room room, int id) {
		new RoomDAOImpl().deleteRoom(room, id);
	}

	public Room getRoom(int id) {
		return new RoomDAOImpl().getRoom(id);
	}

	public void manageSubscription(int roomId, Principal principal) {
		Room person = getRoom(roomId);
		Set<User> users = person.getSubscribers();
		int counter = 0;
		for (User user : users) {
			if (user.getLogin().equals(principal.getName())) {
				counter++;
				users.remove(user);
				deleteSubscriber(user.getId(), roomId);
			}
		}
		if (counter == 0) {
			User user = new UserDAOImpl().getUser(principal.getName());
			users.add(user);
			addSubscriber(user.getId(), roomId);
		}
	}
}

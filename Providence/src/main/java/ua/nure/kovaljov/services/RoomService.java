package ua.nure.kovaljov.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.nure.kovaljov.database.dao.impl.RoomDAOImpl;
import ua.nure.kovaljov.database.dao.impl.TransactionDAOImpl;
import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.entity.dbentity.Person;
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
	public List<Person> getOnlineUsers(String roomName) {
		List<Person> users = new ArrayList<>();
		List<History> hList = new TransactionDAOImpl().getTodayHistory(roomName);
		users.addAll(getPresentUsers(hList));
		return users;
	}
	private Set<Person> getPresentUsers(List<History> hList) {
		Set<Person> present = new HashSet<>();
		for(History h:hList) {
			if (h.getRoom().getDoorState().equalsIgnoreCase("entrance")) {
				present.add(h.getPerson());
			} else if (h.getRoom().getDoorState().equalsIgnoreCase("exit")) {
				present.remove(h.getPerson());
			}
		}
		return present;
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

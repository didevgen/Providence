package ua.nure.kovaljov.services;

import java.util.List;

import ua.nure.kovaljov.database.dao.impl.RoomDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Room;

public class RoomService {
	public List<Room> getRooms() {
		return new RoomDAOImpl().getAllRooms();
	}
	
	public void updateRoom(Room room, int id) {
		new RoomDAOImpl().updateRoom(room, id);
	}

	public void deleteRoom(Room room, int id) {
		new RoomDAOImpl().deleteRoom(room, id);
	}
}

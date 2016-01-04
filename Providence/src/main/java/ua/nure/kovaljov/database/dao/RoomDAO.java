package ua.nure.kovaljov.database.dao;

import java.util.List;

import ua.nure.kovaljov.entity.dbentity.Room;

public interface RoomDAO {
	List<Room> getAllRooms();
	void updateRoom(Room room, int id);
	Room getRoomById(int id);
}

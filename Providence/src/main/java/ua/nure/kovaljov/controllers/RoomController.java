package ua.nure.kovaljov.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.entity.dbentity.Room;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.services.RoomService;

@RestController
public class RoomController {
	private Logger log = LogManager.getLogger(RoomController.class);
	RoomService service = new RoomService();
	
	@RequestMapping(value = "/room/all", method = RequestMethod.POST)
	public List<Room> getAllRooms(Principal principal) {
		List<Room> rooms = service.getRooms();
		User user = new UserDAOImpl().getUser(principal.getName());
		for (Room room : rooms) {
			if (room.getSubscribers().contains(user)){
				room.setSubscribed(true);
			}
		}
		return rooms;
	}
	
	@RequestMapping(value = "/room/update/{id}", method = RequestMethod.POST)
	public void updateRoom(@PathVariable int id, @RequestBody Room room) {
		service.updateRoom(room, id);;
	}
	
	@RequestMapping(value = "/room/delete/{id}", method = RequestMethod.POST)
	public void deleteRoom(@PathVariable int id, @RequestBody Room room) {
		service.deleteRoom(room, id);;
	}
	@RequestMapping(value = "/room/{userId}/subscribe", method = RequestMethod.POST)
	public void updateSubscription( @PathVariable int userId, Principal principal) {
		log.entry(userId);
		service.manageSubscription(userId, principal);
	}
}

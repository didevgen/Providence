package ua.nure.kovaljov.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.services.UserService;

@RestController
public class UserController {
	private Logger log = LogManager.getLogger(UserController.class);
	private UserService service = new UserService();

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public User registerUser(@RequestBody User user) {
		log.entry(user);
		return service.insertUser(user);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
	public User getUser(@PathVariable long userId) {
		log.entry(userId);
		return service.getUser(userId);
	}

	@RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.POST)
	public void deleteUser(@PathVariable long userId) {
		log.entry(userId);
		service.deleteUser(userId);
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user) {
		log.entry(user);
		return service.updateUser(user);
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.POST)
	public List<User> getAllUsers() {
		log.entry();
		return service.getUsers();
	}
	
	@RequestMapping(value = "/user/group/{groupId}", method = RequestMethod.POST)
	public List<User> getUsersNotRelatedToGroup(@PathVariable long groupId) {
		log.entry(groupId);
		return service.getUsersWithoutGroup(groupId);
	}
	
	@RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.POST)
	public void removeGroupFromUser(@PathVariable long userId,@PathVariable long groupId) {
		log.entry(userId,groupId);
		service.removeGroupFromUser(groupId, userId);
	}
}

package ua.nure.kovaljov.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.services.UserService;

@RestController
public class UserController {
	private UserService service = new UserService();

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public User registerUser(@RequestBody User user) {
		System.out.println("registerUser");
		return service.insertUser(user);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
	public User getUser(@PathVariable long userId) {
		System.out.println("getUser");
		return service.getUser(userId);
	}

	@RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.POST)
	public void deleteUser(@PathVariable long userId) {
		System.out.println("deleteUser");
		service.deleteUser(userId);
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user) {
		System.out.println("updateUser");
		return service.updateUser(user);
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.POST)
	public List<User> getAllUsers() {
		System.out.println("getAllUsers");
		return service.getUsers();
	}
}

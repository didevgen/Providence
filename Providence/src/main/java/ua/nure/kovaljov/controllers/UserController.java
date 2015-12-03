package ua.nure.kovaljov.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.User;

@RestController
public class UserController {
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public void registerUser(@PathVariable User user) {
	}
}

package ua.nure.kovaljov.services.ifaces;

import ua.nure.kovaljov.entity.dbentity.User;

public interface UserService {
	public User getUser(String login);
}

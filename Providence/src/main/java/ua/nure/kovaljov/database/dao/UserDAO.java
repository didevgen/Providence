package ua.nure.kovaljov.database.dao;

import ua.nure.kovaljov.entity.dbentity.User;

public interface  UserDAO {
	 User getUser(String login);
}

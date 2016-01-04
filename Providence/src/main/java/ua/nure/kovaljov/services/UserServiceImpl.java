package ua.nure.kovaljov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.services.ifaces.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
     
    @Autowired
    private UserDAO userDAO;
 
    public User getUser(String login) {
        return userDAO.getUser(login);
    }
 
}

package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.utils.HibernateUtil;

@Repository
public class UserDAOImpl implements UserDAO {
    private Session openSession() {
    	Session session = null;
    	session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
 
    public User getUser(String login) {
        List<User> userList = new ArrayList<User>();
        Query query = openSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", login);
        userList = query.list();
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;    
    }
 
}
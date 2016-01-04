package ua.nure.kovaljov.database.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.nure.kovaljov.database.dao.RoleDAO;
import ua.nure.kovaljov.entity.dbentity.Role;
import ua.nure.kovaljov.utils.HibernateUtil;

@Repository
public class RoleDAOImpl implements RoleDAO {
     
     
    private Session getCurrentSession() {
    	Session session = null;
    	session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
 
    public Role getRole(int id) {
        Role role = (Role) getCurrentSession().load(Role.class, id);
        return role;
    }
 
}
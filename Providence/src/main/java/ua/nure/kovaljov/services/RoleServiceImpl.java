package ua.nure.kovaljov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.nure.kovaljov.database.dao.RoleDAO;
import ua.nure.kovaljov.entity.dbentity.Role;
import ua.nure.kovaljov.services.ifaces.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
     
    @Autowired
    private RoleDAO roleDAO;
 
    public Role getRole(int id) {
        return roleDAO.getRole(id);
    }
 
}
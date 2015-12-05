package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.GroupDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.utils.HibernateUtil;

public class GroupDAOImpl extends BaseCRUD implements GroupDAO{

	@Override
	public Group getGroup(long objectId, Class<Group> className) {
		return (Group)super.getObject(objectId, Group.class);
	}

	@Override
	public Group insertGroup(Group obj) {
		return (Group) super.insertObject(obj);
	}

	@Override
	public void deleteGroup(long objectId, String objectName) {
		super.deleteObject(objectId, "Group", "group_id");
	}

	@Override
	public Group updateGroup(Group group) {
		return (Group) super.updateObject(group);
	}

	@Override
	public List<Group> getAllGroups() {
		Session session = null;
		List<Group> objects = new ArrayList<Group>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(Group.class).list();
			for(Group o : objects) {
				Hibernate.initialize(o.getUsers());
				o.getUsers().forEach(item->item.setGroups(null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

}

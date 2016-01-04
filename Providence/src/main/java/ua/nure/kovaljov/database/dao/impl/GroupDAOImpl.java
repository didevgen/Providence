package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import ua.nure.kovaljov.database.dao.GroupDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.utils.HibernateUtil;

public class GroupDAOImpl extends BaseCRUD implements GroupDAO{
	private Logger log = LogManager.getLogger(GroupDAOImpl.class);
	@Override
	public Group getGroup(long objectId, Class<Group> className) {
		Session session = null;
		Group group = new Group();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			group = session.get(Group.class, objectId);
			for (Person person : group.getPersons()) {
				Hibernate.initialize(person);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return group;
	}

	@Override
	public Group insertGroup(Group obj) {
		return (Group) super.insertObject(obj);
	}

	@Override
	public void deleteGroup(long objectId, String objectName) {
		if (objectId ==1) {
			return;
		}
		super.deleteObject(objectId, "Group", "group_id");
	}

	@Override
	public Group updateGroup(Group group) {
		return (Group) super.updateObject(group);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getAllGroups() {
		Session session = null;
		List<Group> objects = new ArrayList<Group>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(Group.class).list();
			for(Group o : objects) {
				Hibernate.initialize(o.getPersons());
				for (Person person : o.getPersons()) {
					person.setGroups(null);
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

}

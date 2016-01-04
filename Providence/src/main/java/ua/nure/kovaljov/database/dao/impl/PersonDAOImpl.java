package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.PersonDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.utils.HibernateUtil;

public class PersonDAOImpl extends BaseCRUD implements PersonDAO {
	private Logger log = LogManager.getLogger(PersonDAOImpl.class);
	@Override
	public Person getPerson(long objectId, Class<Person> className) {
		Session session = null;
		Person person = new Person();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			person = session.get(Person.class, objectId);
			for (Group group : person.getGroups()) {
				Hibernate.initialize(group);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return person;
	}

	@Override
	public Person insertPerson(Person obj) {
		return (Person) super.insertObject(obj);
	}

	@Override
	public void deletePerson(long objectId, String objectName) {
		super.deleteObject(objectId, objectName, "person_id");
	}

	@Override
	public Person updatePerson(Person person) {
		return (Person) super.updateObject(person);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPersons() {
		Session session = null;
		List<Person> objects = new ArrayList<Person>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(Person.class).list();
			for (Person o : objects) {
				Hibernate.initialize(o.getGroups());
				for (Group group : o.getGroups()) {
					group.setPersons(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

	@Override
	public List<Person> userWithoutGroup() {
		return getAllPersons();
	}

	@SuppressWarnings("unchecked")
	public Person getPersonByCardNumber(long cardNumber) {
		Session session = null;
		List<Person> person = new ArrayList<Person>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			person = session.createCriteria(Person.class).add(Restrictions.eq("cardNumber", cardNumber)).list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return person.size() == 0 ? null : person.get(0);
	}

	@Override
	public void deletePersonByCardNumber(long cardId, String objectName) {
		super.deleteObject(cardId, objectName, "cardNumber");
	}

	@Override
	public Person getPersonForAuth(String email) {
		return null;
	}

}

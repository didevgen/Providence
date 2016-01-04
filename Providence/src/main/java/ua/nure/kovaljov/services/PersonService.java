package ua.nure.kovaljov.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.kovaljov.database.dao.PersonDAO;
import ua.nure.kovaljov.database.dao.impl.PersonDAOImpl;
import ua.nure.kovaljov.database.dao.impl.UserDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.entity.dbentity.User;
import ua.nure.kovaljov.websockets.container.WSContainer;

public class PersonService {
	private Logger log = LogManager.getLogger(PersonService.class);

	public Person insertPerson(Person person) {
		log.entry(person);
		String card = String.valueOf(person.getCardNumber());
		if (card.length() == 7) {
			log.info("card.length() == 7");
			WSContainer.sendToAllDesktopConnectedSessions("Insert:" + card);
		}
		return new PersonDAOImpl().insertPerson(person);
	}

	public Person updatePerson(Person person) {
		PersonDAO dao = new PersonDAOImpl();
		long cardId = getPerson(person.getPersonId()).getCardNumber();
		String card = String.valueOf(cardId);
		if (card.length() == 7) {
			WSContainer.sendToAllDesktopConnectedSessions("Delete:" + card);
		}
		card = String.valueOf(person.getCardNumber());
		if (card.length() == 7) {
			WSContainer.sendToAllDesktopConnectedSessions("Insert:" + card);
		}
		for (Group gr : person.getGroups()) {
			gr.setPersons(null);
		}
		return dao.updatePerson(person);
	}

	public void deletePerson(long userId) {
		log.entry(userId);
		long cardId = getPerson(userId).getCardNumber();
		new PersonDAOImpl().deletePerson(userId, "Person");
		String card = String.valueOf(cardId);
		if (card.length() == 7) {
			WSContainer.sendToAllDesktopConnectedSessions("Delete:" + card);
		}
	}

	public Person getPerson(long userId) {
		return new PersonDAOImpl().getPerson(userId, Person.class);
	}

	public List<Person> getPersons(Principal principal) {
		List<Person> persons = new PersonDAOImpl().getAllPersons();
		User user = new UserDAOImpl().getUser(principal.getName());
		for (Person person : persons) {
			if (user.getSubsribedPersons().contains(person)) {
				person.setSubscribed(true);
			}
		}
		return persons;
	}

	public List<Person> getPersonsWithoutGroup(long groupId) {
		List<Person> persons = new PersonDAOImpl().getAllPersons();
		Group group = new Group();
		group.setId(groupId);
		List<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (!person.getGroups().contains(group)) {
				result.add(person);
			}
		}
		return result;
	}

	public void removeGroupFromPerson(long groupId, long userId) {
		List<Person> persons = new PersonDAOImpl().getAllPersons();
		Group group = new Group();
		group.setId(groupId);
		for (Person person : persons) {
			if (person.getPersonId() == userId && person.getGroups().contains(group)) {
				person.getGroups().remove(group);
				new PersonDAOImpl().updatePerson(person);
				return;
			}
		}
	}
}

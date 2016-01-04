package ua.nure.kovaljov.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.services.PersonService;

@RestController
public class PersonController {
	private Logger log = LogManager.getLogger(PersonController.class);
	private PersonService service = new PersonService();

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public Person registerPerson(@RequestBody Person person) {
		log.entry(person);
		return service.insertPerson(person);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
	public Person getPerson(@PathVariable long userId) {
		log.entry(userId);
		return service.getPerson(userId);
	}

	@RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.POST)
	public void deletePerson(@PathVariable long userId) {
		log.entry(userId);
		service.deletePerson(userId);
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public Person updatePerson(@RequestBody Person person) {
		log.entry(person);
		return service.updatePerson(person);
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.POST)
	public List<Person> getAllPersons(Principal principal) {
		log.entry();
		log.info(principal.getName());
		return service.getPersons();
	}
	
	@RequestMapping(value = "/user/group/{groupId}", method = RequestMethod.POST)
	public List<Person> getPersonsNotRelatedToGroup(@PathVariable long groupId) {
		log.entry(groupId);
		return service.getPersonsWithoutGroup(groupId);
	}
	
	@RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.POST)
	public void removeGroupFromPerson(@PathVariable long userId,@PathVariable long groupId) {
		log.entry(userId,groupId);
		service.removeGroupFromPerson(groupId, userId);
	}
}

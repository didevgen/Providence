package ua.nure.kovaljov.database.dao;

import java.util.List;

import ua.nure.kovaljov.entity.dbentity.Person;

public interface PersonDAO {
	Person getPerson(long objectId,Class<Person> className);

	Person insertPerson(Person obj);

	void deletePerson(long objectId, String objectName);

	Person updatePerson(Person person);
	
	List<Person> getAllPersons();
	
	List<Person> userWithoutGroup();
	
	void deletePersonByCardNumber(long cardId, String objectName);
	
	Person getPersonForAuth(String email);
}

package ua.nure.kovaljov.database.dao;

import java.util.List;

import ua.nure.kovaljov.entity.dbentity.Group;

public interface GroupDAO {
	Group getGroup(long objectId,Class<Group> className);

	Group insertGroup(Group obj);

	void deleteGroup(long objectId, String objectName);

	Group updateGroup(Group group);
	
	List<Group> getAllGroups();
}

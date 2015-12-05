package ua.nure.kovaljov.services;

import java.util.List;

import ua.nure.kovaljov.database.dao.impl.GroupDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Group;

public class GroupService {
	public Group insertGroup(Group group) {
		return new GroupDAOImpl().insertGroup(group);
	}
	public Group updateGroup(Group Group) {
		return new GroupDAOImpl().updateGroup(Group);
	}
	public void deleteGroup(long GroupId) {
		new GroupDAOImpl().deleteGroup(GroupId, "Group");
	}
	public Group getGroup(long GroupId) {
		return new GroupDAOImpl().getGroup(GroupId, Group.class);
	}
	public List<Group> getGroups() {
		return new GroupDAOImpl().getAllGroups();
	}
}

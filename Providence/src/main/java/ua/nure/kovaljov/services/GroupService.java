package ua.nure.kovaljov.services;

import java.util.List;

import ua.nure.kovaljov.database.dao.impl.GroupDAOImpl;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.User;

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
		Group group = new GroupDAOImpl().getGroup(GroupId, Group.class);
		if (group!=null) {
			for (User u : group.getUsers()) {
				u.setGroups(null);
			}
		}
		return group;
	}
	public List<Group> getGroups() {
		return new GroupDAOImpl().getAllGroups();
	}
}

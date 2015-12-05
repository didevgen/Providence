package ua.nure.kovaljov.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.services.GroupService;
@RestController
public class GroupController {
	private GroupService service = new GroupService();

	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public Group registerGroup(@RequestBody Group group) {
		System.out.println("registerGroup");
		return service.insertGroup(group);
	}

	@RequestMapping(value = "/group/{groupId}", method = RequestMethod.POST)
	public Group getGroup(@PathVariable long groupId) {
		System.out.println("getGroup");
		return service.getGroup(groupId);
	}

	@RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.POST)
	public void deleteGroup(@PathVariable long groupId) {
		System.out.println("deleteGroup");
		service.deleteGroup(groupId);
	}

	@RequestMapping(value = "/group/update", method = RequestMethod.POST)
	public Group updateGroup(@RequestBody Group group) {
		System.out.println("updateGroup");
		return service.updateGroup(group);
	}
	
	@RequestMapping(value = "/group/all", method = RequestMethod.POST)
	public List<Group> getAllGroups() {
		System.out.println("getAllGroups");
		return service.getGroups();
	}
}

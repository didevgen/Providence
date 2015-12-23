package ua.nure.kovaljov.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private Logger log = LogManager.getLogger(GroupController.class);
	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public Group registerGroup(@RequestBody Group group) {
		log.entry(group);
		return service.insertGroup(group);
	}

	@RequestMapping(value = "/group/{groupId}", method = RequestMethod.POST)
	public Group getGroup(@PathVariable long groupId) {
		log.entry(groupId);
		return service.getGroup(groupId);
	}

	@RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.POST)
	public void deleteGroup(@PathVariable long groupId) {
		log.entry(groupId);
		service.deleteGroup(groupId);
	}

	@RequestMapping(value = "/group/update", method = RequestMethod.POST)
	public Group updateGroup(@RequestBody Group group) {
		log.entry(group);
		return service.updateGroup(group);
	}
	
	@RequestMapping(value = "/group/all", method = RequestMethod.POST)
	public List<Group> getAllGroups() {
		log.entry();
		return service.getGroups();
	}
}

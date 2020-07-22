package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Group;
import com.app.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	public String addGroup(Group gp) {

		if (!gp.getName().equals("")) {
			groupRepository.save(gp);
		}

		return "Group added : " + gp.getName();
	}

	public Group getGroupByid(String id) {
		Group group = new Group();
		List<Group> list = groupRepository.findAll();
		boolean find = false;
		int i = 0;

		while (find == false && list.size() > i) {

			if (list.get(i).getId().equals(id)) {
				group = list.get(i);
				find = true;
			} else {
				i++;
			}
		}
		return group;
	}

	public String deleteGroup(String id) {

		List<Group> group = groupRepository.findAll();
		Group gp = new Group();
		boolean find = false;
		int i = 0;

		while (find == false && group.size() > i) {

			if (group.get(i).getId().equals(id)) {
				gp = group.get(i);
				find = true;
			} else {
				i++;
			}
		}

		if (find == true) {
			groupRepository.delete(gp);
			return "Group deleted";
		} else {
			return "Group not deleted";
		}
	}
}

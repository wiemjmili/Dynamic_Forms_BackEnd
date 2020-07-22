package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.User;
import com.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	public User currentUser;

	public String verifUser(String username, String password) {
		List<User> Users = userRepository.findAll();
		boolean find = false;
		int j = 0;

		while (find == false && Users.size() > j) {

			if (Users.get(j).getUsername().equals(username) && Users.get(j).getPassword().equals(password)) {
				if (Users.get(j).getUsername().equals("Admin")) {
					currentUser = Users.get(j);
					return "admin";
				} else {
					find = true;
					currentUser = Users.get(j);
				}
			} else {
				j++;
			}
		}

		if (find == true) {
			return "True";
		} else {
			return "False";
		}
	}

	public String addUser(User user) {
		if (!user.getUsername().equals("")) {
			userRepository.save(user);
		}
		return "User added" + user.getUsername();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public List<User> getAllUser() {
		List<User> list = userRepository.findAll();
		return list;
	}

	public User getUserByid(String id) {
		User user = new User();
		List<User> list = userRepository.findAll();
		boolean find = false;
		int i = 0;

		while (find == false && list.size() > i) {

			if (list.get(i).getId().equals(id)) {
				user = list.get(i);
				find = true;
			} else {
				i++;
			}

		}
		return user;
	}

	public String deleteUser(String id) {

		List<User> users = userRepository.findAll();
		User user = new User();
		boolean find = false;
		int i = 0;

		while (find == false && users.size() > i) {

			if (users.get(i).getId().equals(id)) {
				user = users.get(i);
				find = true;
			} else {
				i++;
			}

		}

		if (find == true) {
			userRepository.delete(user);
			return "User deleted";

		} else {
			return "User not deleted";
		}
	}

}

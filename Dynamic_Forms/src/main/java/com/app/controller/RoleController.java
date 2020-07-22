package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Role;
import com.app.service.RoleService;

@RestController
@RequestMapping("/api/role")

public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/addRole")
	public ResponseEntity<String> addRole(@RequestBody Role role) {
		try {

			return ResponseEntity.accepted().body(roleService.addRole(role));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@GetMapping("/getAllRoles")
	public ResponseEntity<List<Role>> getAllRoles() {
		try {

			return ResponseEntity.accepted().body(roleService.getAllRoles());

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@GetMapping("/getRole_Byid/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") String id) {
		try {

			return ResponseEntity.accepted().body(roleService.getRoleById(id));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@DeleteMapping("/deleteRole/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable(value = "id") String id) {
		try {

			return ResponseEntity.accepted().body(roleService.deleteRole(id));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

}

package controllers;

import java.util.List;

import models.entities.*;
import models.dto.*;
import models.projection.*;

import repository.*;
import exception.*;
import services.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RoleController {


	private final RoleService roleService;

	RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping("/roles")
	List<Role> getRoles() {
		return roleService.findAll();
	}

	@PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

	@PutMapping("/roles/{id}")
    public Role updateRole(@RequestBody Role newRole, @PathVariable Long id) {
        return roleService.updateRole(newRole, id);
    }

	@DeleteMapping("/roles/{id}")
	void deleteRole(@PathVariable Long id) {
		roleService.deleteById(id);
	}

}

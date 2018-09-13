package controllers;

import java.util.List;

import models.entities.Role;
import models.dto.RoleDTO;
import models.dto.PersonDTO;

import services.RoleService;

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
	List<RoleDTO> getRoles() {
		return roleService.findAll();
	}

	@GetMapping("/roles/{roleId}")
	RoleDTO getRoles(@PathVariable Long roleId) {
		return roleService.findById(roleId);
	}

	@PostMapping("/roles")
    public RoleDTO createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

	@PutMapping("/roles/{id}")
    public RoleDTO updateRole(@RequestBody Role newRole, @PathVariable Long id) {
        return roleService.updateRole(newRole, id);
    }

	@DeleteMapping("/roles/{id}")
	void deleteRole(@PathVariable Long id) {
		roleService.deleteById(id);
	}

}

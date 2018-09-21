package controllers;

import java.util.List;

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
import org.springframework.security.access.annotation.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	private final RoleService roleService;

	RoleController(RoleService roleService) {
		this.roleService = roleService;
	}


	@GetMapping("/roles")
	List<RoleDTO> getRoles() {
		logger.info("Called getRoles()");
		return roleService.findAll();
	}

	@GetMapping("/roles/{roleId}")
	RoleDTO getRoles(@PathVariable Long roleId) {
		logger.info("Called findById(roleId)");
		return roleService.findById(roleId);
	}

	@PostMapping("/roles")
    public RoleDTO createRole(@RequestBody RoleDTO role) {
		logger.info("Called createRole(role)");
        return roleService.createRole(role);
    }

	@PutMapping("/roles/{id}")
    public RoleDTO updateRole(@RequestBody RoleDTO newRole, @PathVariable Long id) {
		logger.info("Called updateRole(newRole, id)");
	    return roleService.updateRole(newRole, id);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/roles/{id}")
	void deleteRole(@PathVariable Long id) {
		logger.info("Called deleteById(id)");
		roleService.deleteRole(id);
	}

}

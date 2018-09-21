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
	ResponseEntity<?> getRoles() {
		logger.info("Called getRoles()");
		return new ResponseEntity<List<RoleDTO>>>(roleService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/roles/{roleId}")
	ResponseEntity<?> getRoles(@PathVariable Long roleId) {
		logger.info("Called findById(roleId)");
		return new ResponseEntity<RoleDTO>(roleService.findById(roleId), HttpStatus.OK);
	}

	@PostMapping("/roles")
    ResponseEntity<?> createRole(@RequestBody RoleDTO role) {
		logger.info("Called createRole(role)");

		return ResponseEntity<RoleDTO>(roleService.createRole(role), HttpStatus.OK);
    }

	@PutMapping("/roles/{id}")
    ResponseEntity<?> updateRole(@RequestBody RoleDTO newRole, @PathVariable Long id) {
		logger.info("Called updateRole(newRole, id)");
	    return ResponseEntity<RoleDTO>(roleService.updateRole(role), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/roles/{id}")
	void deleteRole(@PathVariable Long id) {
		logger.info("Called deleteById(id)");
		roleService.deleteRole(id);
	}

}

package services;

import java.util.List;
import java.util.Set;

import models.entities.*;
import models.dto.*;
import models.projection.*;

import repository.*;
import exception.*;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonRepository personRepository;


	public List<Role> findAll() {
		return roleRepository.findAll();
	 }

	public Role createRole(Role newRole) {
		if(roleRepository.existsByRole(newRole.getRole())){
			throw new ResourceAlreadyExistsException("Role already exists with role: " + newRole);
		}

		return roleRepository.save(newRole);
	}

	public Role findById(Long id) {
		return roleRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
	}

	public Role updateRole(Role newRole, Long id) {

		return roleRepository.findById(id)
			.map(role -> {
				role.setRole(newRole.getRole());
				return roleRepository.save(role);
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
	}

	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	public List<Person> findRoleOwners(Long id){
		return personRepository.findByRolesIn(roleRepository.findById(id).get().getPersons());
	}

	public ResponseEntity<?> deleteRole(Long roleId) {
		return roleRepository.findById(roleId)
				.map(role -> {
					roleRepository.delete(role);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
	}
 }

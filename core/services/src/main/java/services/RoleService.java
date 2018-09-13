package services;

import java.util.List;
import java.util.Set;

import models.entities.*;
import models.dto.*;
import exception.*;

import mappers.PersonMapper;
import mappers.RoleMapper;

import repository.RoleRepository;
import repository.PersonRepository;

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

	private RoleMapper roleMapper = new RoleMapper();
	private PersonMapper personMapper = new PersonMapper();

	public List<RoleDTO> findAll() {
		return roleMapper.mapToRoleDTOList(roleRepository.findAll());
	 }

	public RoleDTO createRole(Role newRole) {
		if(roleRepository.existsByRole(newRole.getRole())){
			throw new ResourceAlreadyExistsException("Role already exists with role: " + newRole);
		}

		return roleMapper.mapToRoleDTO(roleRepository.save(newRole));
	}

	public RoleDTO findById(Long id) {
		Role role = roleRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

		return roleMapper.mapToRoleDTO(role);
	}

	public RoleDTO updateRole(Role newRole, Long id) {

		return roleRepository.findById(id)
			.map(role -> {
				role.setRole(newRole.getRole());
				return roleMapper.mapToRoleDTO(roleRepository.save(role));
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
	}

	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	public List<PersonDTO> findRoleOwners(Long id){
		System.out.println(personRepository.findByRolesIn(roleRepository.findById(id).get().getPersons()));
		return personMapper.mapToPersonDTOList(personRepository.findByRolesIn(roleRepository.findById(id).get().getPersons()));
	}

	public ResponseEntity<?> deleteRole(Long roleId) {
		return roleRepository.findById(roleId)
				.map(role -> {
					roleRepository.delete(role);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
	}
 }

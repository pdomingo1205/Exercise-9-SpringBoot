package services;

import java.util.List;
import java.util.Set;

import models.entities.Role;
import models.dto.PersonDTO;
import models.dto.RoleDTO;

import exception.ResourceNotFoundException;
import exception.ResourceAlreadyExistsException;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonRepository personRepository;
	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

	private RoleMapper roleMapper = new RoleMapper();
	private PersonMapper personMapper = new PersonMapper();

	public List<RoleDTO> findAll() {
		logger.info("Called findAll()");
		return roleMapper.mapToRoleDTOList(roleRepository.findAll());
	 }

	public RoleDTO createRole(RoleDTO newRole) {
		logger.info("Called createRole(newRole)");
		if(roleRepository.existsByRole(newRole.getRole())){
			throw new ResourceAlreadyExistsException("Role already exists with role: " + newRole);
		}

		return roleMapper.mapToRoleDTO(roleRepository.save(roleMapper.mapToRole(newRole)));
	}

	public RoleDTO findById(Long id) {
		logger.info("Called findById(id)");
		Role role = roleRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

		return roleMapper.mapToRoleDTO(role);
	}

	public RoleDTO updateRole(RoleDTO newRole, Long id) {
		logger.info("Called updateRole(newRole, id)");

		return roleRepository.findById(id)
			.map(role -> {
				role.setRole(newRole.getRole());
				return roleMapper.mapToRoleDTO(roleRepository.save(role));
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
	}

	public void deleteById(Long id) {
		logger.info("Called deleteById(id)");
		
		roleRepository.deleteById(id);
	}

	public ResponseEntity<?> deleteRole(Long roleId) {
		return roleRepository.findById(roleId)
				.map(role -> {
					roleRepository.delete(role);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
	}
 }

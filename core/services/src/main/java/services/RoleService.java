package services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import models.entities.Role;
import models.dto.PersonDTO;
import models.dto.RoleDTO;

import exception.ResourceNotFoundException;
import exception.ResourceAlreadyExistsException;

import repository.RoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapperFacade = mapperFactory.getMapperFacade();

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);


	public List<RoleDTO> findAll() {
		logger.info("Called findAll()");
		return roleRepository.findAll().stream().map(role -> mapperFacade.map(role, RoleDTO.class)).collect(Collectors.toList());
	 }

	public RoleDTO createRole(RoleDTO newRole) {
		logger.info("Called createRole(newRole)");
		if(roleRepository.existsByRole(newRole.getRole())){
			throw new ResourceAlreadyExistsException("Role already exists with role: " + newRole);
		}

		return mapperFacade.map(roleRepository.save(mapperFacade.map(newRole,Role.class)), RoleDTO.class);
	}

	public RoleDTO findById(Long id) {
		logger.info("Called findById(id)");
		Role role = roleRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

		return mapperFacade.map(role,RoleDTO.class);
	}

	public RoleDTO updateRole(RoleDTO newRole, Long id) {
		logger.info("Called updateRole(newRole, id)");

		return roleRepository.findById(id)
			.map(role -> {
				role.setRole(newRole.getRole());
				return mapperFacade.map(roleRepository.save(role),RoleDTO.class);
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

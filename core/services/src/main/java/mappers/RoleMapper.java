package mappers;


import java.util.List;
import java.util.Set;
import java.util.HashSet;

import models.entities.*;
import models.dto.*;
import models.projection.*;


import javax.validation.Valid;


public class RoleMapper {


	public RoleMapper(){
	}

	public Set<Role> createRoleSet(Set<RoleDTO> rolesDTO) {
        Set<Role> roles = new HashSet<>();
        rolesDTO.forEach(role -> roles.add(mapToRole(role)));
        return roles;
    }

	public Set<PersonRolesDTO> createPersonRoleSetDTO(Set<Role> roles) {
		Set<PersonRolesDTO> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToPersonRolesDTO(role)));
		return rolesDTO;
	}

	public Set<RoleDTO> createRoleSetDTO(Set<Role> roles) {
		Set<RoleDTO> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToRoleDTO(role)));
		return rolesDTO;
	}

	public RoleDTO mapToRoleDTO(Role role){
		RoleDTO roleDTO = new RoleDTO();
		roleDTO = new RoleDTO(role.getRoleId(), role.getRole());

		Set<PersonDTO> persons = new HashSet<>();
		role.getPersons().forEach(personDTO -> persons.add(projectToPersonDTO(personDTO)));
		roleDTO.setPersons(persons);

		return roleDTO;
	}

	public PersonRolesDTO mapToPersonRolesDTO(Role role){
		PersonRolesDTO personRoleDTO = new PersonRolesDTO(role.getRoleId(), role.getRole());
		return personRoleDTO;
	}

	public Role mapToRole(RoleDTO roleDTO) {
		Role role = new Role();
		role.setRoleId(roleDTO.getRoleId());
		role.setRole(roleDTO.getRole());

		Set<Person> persons = new HashSet<>();
		roleDTO.getPersons().stream().forEach(person -> persons.add(projectToPerson(person)));
		role.setPersons(persons);
		return role;
	}


	private Person projectToPerson(PersonDTO personDTO) {
		Person person = new Person();
		person.setId(personDTO.getPersonId());

		return person;
	}

	private PersonDTO projectToPersonDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setPersonId(person.getId());

		return personDTO;
	}


}

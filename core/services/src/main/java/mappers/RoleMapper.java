package mappers;


import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import models.entities.Role;
import models.entities.Person;
import models.dto.RoleDTO;
import models.dto.PersonDTO;
import models.dto.NameDTO;
import models.projection.PersonLastName;

import models.projection.PersonRoles;


import javax.validation.Valid;


public class RoleMapper {


	public RoleMapper(){
	}

	public Set<Role> createRoleSet(Set<RoleDTO> rolesDTO) {
        Set<Role> roles = new HashSet<>();
        rolesDTO.forEach(role -> roles.add(mapToRole(role)));
        return roles;
    }

	public Set<PersonRoles> createPersonRoleSetDTO(Set<Role> roles) {
		Set<PersonRoles> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToPersonRolesDTO(role)));
		return rolesDTO;
	}

	public Set<RoleDTO> createRoleSetDTO(Set<Role> roles) {
		Set<RoleDTO> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToRoleDTO(role)));
		return rolesDTO;
	}

	public RoleDTO mapToRoleDTO(Role role){
		RoleDTO roleDTO = new RoleDTO(role.getRoleId(), role.getRole());

		Set<PersonLastName> persons = new HashSet<>();
		role.getPersons().forEach(person -> persons.add(projectToPersonLastName(person)));
		roleDTO.setPersons(persons);

		return roleDTO;
	}

	public PersonRoles mapToPersonRolesDTO(Role role){
		PersonRoles personRoleDTO = new PersonRoles(role.getRoleId(), role.getRole());
		return personRoleDTO;
	}

	public Role mapToRole(RoleDTO roleDTO) {
		Role role = new Role();
		role.setRoleId(roleDTO.getRoleId());
		role.setRole(roleDTO.getRole());

		Set<Person> persons = new HashSet<>();
		roleDTO.getPersons().stream().forEach(person ->{
			persons.add(projectToPerson(person));
		});
		role.setPersons(persons);
		return role;
	}

	public List<RoleDTO> mapToRoleDTOList(List<Role> roles){
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		roles.stream().forEach(role -> roleDTOs.add(mapToRoleDTO(role)));

		return roleDTOs;
	}


	private Person projectToPerson(PersonLastName personLastName) {
		Person person = new Person();
		person.setId(personLastName.getPersonId());

		return person;
	}

	private PersonLastName projectToPersonLastName(Person person) {
		PersonLastName personLastName = new PersonLastName();
		personLastName.setPersonId(person.getId());
		NameDTO name = new NameDTO();
		name.setFirstName(person.getName().getFirstName());
		name.setMiddleName(person.getName().getMiddleName());
		name.setLastName(person.getName().getLastName());
		personLastName.setName(name);

		return personLastName;
	}


}

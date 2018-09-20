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
import models.dto.PersonDTO;

import models.projection.PersonRoles;


import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleMapper {

	private static final Logger logger = LoggerFactory.getLogger(PersonMapper.class);

	public RoleMapper(){
	}

	public Set<Role> createRoleSet(Set<RoleDTO> rolesDTO) {
		logger.info("Called createRoleSet(rolesDTO)");

        Set<Role> roles = new HashSet<>();
        rolesDTO.forEach(role -> roles.add(mapToRole(role)));
        return roles;
    }

	public Set<RoleDTO> createPersonRoleSetDTO(Set<Role> roles) {
		logger.info("Called createPersonRoleSetDTO(roles)");

		Set<RoleDTO> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToPersonRolesDTO(role)));
		return rolesDTO;
	}

	public Set<RoleDTO> createRoleSetDTO(Set<Role> roles) {
		logger.info("Called createRoleSetDTO(roles)");

		Set<RoleDTO> rolesDTO = new HashSet<>();
		roles.forEach(role -> rolesDTO.add(mapToRoleDTO(role)));
		return rolesDTO;
	}

	public RoleDTO mapToRoleDTO(Role role){
		logger.info("Called mapToRoleDTO(roles)");
		RoleDTO roleDTO = new RoleDTO(role.getRoleId(), role.getRole());

		Set<PersonDTO> persons = new HashSet<>();
		role.getPersons().forEach(person -> persons.add(projectToPersonDTO(person)));
		//roleDTO.setPersons(persons);

		return roleDTO;
	}

	public RoleDTO mapToPersonRolesDTO(Role role){
		logger.info("Called mapToPersonRolesDTO(role)");

		RoleDTO personRoleDTO = new RoleDTO(role.getRoleId(), role.getRole());
		return personRoleDTO;
	}

	public Role mapToRole(RoleDTO roleDTO) {
		logger.info("Called mapToRole(roleDTO)");

		Role role = new Role();
		role.setRoleId(roleDTO.getRoleId());
		role.setRole(roleDTO.getRole());

		//Set<Person> persons = new HashSet<>();
		/*roleDTO.getPersons().stream().forEach(person ->{
			persons.add(projectToPerson(person));
		});*/
		//role.setPersons(persons);
		return role;
	}

	public List<RoleDTO> mapToRoleDTOList(List<Role> roles){
		logger.info("Called mapToRoleDTOList(roles)");
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		roles.stream().forEach(role -> roleDTOs.add(mapToRoleDTO(role)));

		return roleDTOs;
	}


	private Person projectToPerson(PersonDTO personLastName) {
		logger.info("Called projectToPerson(personLastName)");
		Person person = new Person();
		//person.setId(personLastName.getPersonId());

		return person;
	}

	private PersonDTO projectToPersonDTO(Person person) {
		logger.info("Called projectToPerson(person)");

		PersonDTO personLastName = new PersonDTO();
		//personLastName.setPersonId(person.getId());
		NameDTO name = new NameDTO();
		name.setFirstName(person.getName().getFirstName());
		name.setMiddleName(person.getName().getMiddleName());
		name.setLastName(person.getName().getLastName());
		personLastName.setName(name);

		return personLastName;
	}


}

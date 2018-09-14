package services;

import java.util.List;
import java.util.Set;
import java.util.Date;

import models.entities.Person;
import models.entities.ContactInfo;
import models.entities.Role;

import models.dto.*;
import models.projection.*;

import repository.PersonRepository;
import repository.RoleRepository;
import repository.ContactInfoRepository;
import exception.ResourceNotFoundException;
import exception.ResourceAlreadyExistsException;
import mappers.PersonMapper;
import mappers.RoleMapper;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ContactInfoRepository contactInfoRepository;

	private PersonMapper personMapper = new PersonMapper();
	private RoleMapper roleMapper = new RoleMapper();

	public List<PersonDTO> findAll() {
		return personMapper.mapToPersonDTOList(personRepository.findAll());
	 }

	public PersonDTO createPerson(PersonDTO newPerson) {
		return personMapper.mapToPersonDTO(personRepository.save(personMapper.mapToPerson(newPerson)));
	}

	public PersonDTO findById(Long id) {
		Person person = personRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));

		return personMapper.mapToPersonDTO(person);
	}

	public List<PersonDTO> findByLastName(String lastName) {
		return personMapper.mapToPersonDTOList(personRepository.findAllPersonByNameLastName(lastName));
	}

	public NameDTO findNameById(Long id) {
		return personMapper.mapToNameDTO(personRepository.findById(id).get().getName());
	}


	public List<PersonLastName> findAllSortBy(String order){
		List<Person> persons;

		if(order.equals("ASC")){
			persons = personRepository.findAllByOrderByNameLastNameAsc();
		}else if(order.equals("DESC")){
			persons = personRepository.findAllByOrderByNameLastNameDesc();
		}else{
			throw new ResourceNotFoundException("Order not found order:" + order);
		}
		return personMapper.sortLastNameDTO(persons);
	}

	public List<PersonGwa> findAllSortByGWA(String order){
		List<Person> persons;

		if(order.equals("ASC")){
			persons = personRepository.findAllByOrderByGWAAsc();
		}else if(order.equals("DESC")){
			persons = personRepository.findAllByOrderByGWADesc();
		}else{
			throw new ResourceNotFoundException("Order not found order:" + order);
		}
		return personMapper.sortGwaDTO(persons);
	}

	public List<PersonDateHired> findAllSortByDateHired(String order){
		List<Person> persons;

		if(order.equals("ASC")){
			persons = personRepository.findAllByOrderByDateHiredAsc();
		}else if(order.equals("DESC")){
			persons = personRepository.findAllByOrderByDateHiredDesc();
		}else{
			throw new ResourceNotFoundException("Order not found order:" + order);
		}
		return personMapper.sortDateHiredDTO(persons);
	}

	public Set<PersonRoles> findRolesByPersonId(@PathVariable Long id) {
		return roleMapper.createPersonRoleSetDTO(personRepository.findById(id).get().getRoles());
	}

	public AddressDTO findAddressByPersonId(@PathVariable Long id) {
		return personMapper.mapToAddressDTO(personRepository.findById(id).get().getAddress());
	}

	public Double findGWAByPersonId(@PathVariable Long id) {
		return personRepository.findById(id).get().getGWA();
	}

	public Date findDateHiredByPersonId(@PathVariable Long id) {
		return personRepository.findById(id).get().getDateHired();
	}

	public Date findBirthDayByPersonId(@PathVariable Long id) {
		return personRepository.findById(id).get().getbDay();
	}


	public PersonDTO updatePerson(PersonDTO newPerson, Long id) {

		return personRepository.findById(id)
			.map(person -> {
				person.setName(personMapper.mapToName(newPerson.getName()));
				person.setbDay(newPerson.getbDay());
				person.setGWA(newPerson.getGWA());
				person.setCurrEmployed(newPerson.getCurrEmployed());
				person.setAddress(personMapper.mapToAddress(newPerson.getAddress()));
				person.setDateHired(newPerson.getDateHired());
				return personMapper.mapToPersonDTO(personRepository.save(person));
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
	}

	public Set<RoleDTO> addRole(Long id, RoleDTO newRole) {

		return personRepository.findById(id)
			.map(person -> {

				Role addRole = roleMapper.mapToRole(newRole);

				if(roleRepository.existsByRole(addRole.getRole())){
					 addRole = roleRepository.findByRole(newRole.getRole());
					 if(!person.getRoles().contains(addRole)){
						 addRole.getPersons().add(person);
						 roleRepository.save(addRole);

					 }else{
						 throw new ResourceAlreadyExistsException("Person already has Role: " + newRole);
					 }
				}
				else{
					throw new ResourceNotFoundException("Role not found with Role:" + newRole);
				}

				person.getRoles().add(addRole);
				return roleMapper.createRoleSetDTO(personRepository.save(person).getRoles());
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
	}

	public List<ContactInfo> findPersonContacts(Long personId) {
		return contactInfoRepository.findByPersonId(personId);
	}

	public Set<Role> findPersonRoles(Long personId) {
		return personRepository.findById(personId).get().getRoles();
	}

	public void deleteById(Long id) {
		personRepository.deleteById(id);
	}

	public ResponseEntity<?> deletePerson(Long personId) {
		return personRepository.findById(personId)
				.map(person -> {
					personRepository.delete(person);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId));
	}
 }

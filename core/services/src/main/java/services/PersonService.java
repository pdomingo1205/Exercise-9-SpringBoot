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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ContactInfoRepository contactInfoRepository;

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	private PersonMapper personMapper = new PersonMapper();
	private RoleMapper roleMapper = new RoleMapper();

	public List<PersonDTO> findAll() {
		logger.info("Called findAll()");
		return personMapper.mapToPersonDTOList(personRepository.findAll());
	 }

	public PersonDTO createPerson(PersonDTO newPerson) {
		logger.info("Called createPerson(newPerson)");
		return personMapper.mapToPersonDTO(personRepository.save(personMapper.mapToPerson(newPerson)));
	}

	public PersonDTO findById(Long id) {
		logger.info("Called findById(id)");

		Person person = personRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));

		return personMapper.mapToPersonDTO(person);
	}

	public List<PersonDTO> findByLastName(String lastName) {
		logger.info("Called findByLastName(lastName)");
		return personMapper.mapToPersonDTOList(personRepository.findAllPersonByNameLastName(lastName));
	}

	public NameDTO findNameById(Long id) {
		logger.info("Called findNameById(id)");
		return personMapper.mapToNameDTO(personRepository.findById(id).get().getName());
	}


	public List<PersonDTO> findAllSortBy(String order){
		logger.info("Called findAllSortBy(order)");
		List<Person> persons;

		if(order.equals("ASC")){
			persons = personRepository.findAllByOrderByNameLastNameAsc();
		}else if(order.equals("DESC")){
			persons = personRepository.findAllByOrderByNameLastNameDesc();
		}else{
			logger.warn("Order not found :" + order);
			throw new ResourceNotFoundException("Order not found order:" + order);
		}

		return personMapper.sortLastNameDTO(persons);
	}

	public List<PersonDTO> findAllSortByGWA(String order){
		logger.info("Called findAllSortByGWA(order)");
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

	public List<PersonDTO> findAllSortByDateHired(String order){
		logger.info("Called findAllSortByDateHired(order)");

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

	public Set<RoleDTO> findRolesByPersonId(@PathVariable Long id) {
		logger.info("Called findRolesByPersonId(id)");
		return roleMapper.createPersonRoleSetDTO(personRepository.findById(id).get().getRoles());
	}

	public AddressDTO findAddressByPersonId(@PathVariable Long id) {
		logger.info("Called findAddressByPersonId(id)");
		return personMapper.mapToAddressDTO(personRepository.findById(id).get().getAddress());
	}

	public Double findGWAByPersonId(@PathVariable Long id) {
		logger.info("Called findGWAByPersonId(id)");
		return personRepository.findById(id).get().getGWA();
	}

	public Date findDateHiredByPersonId(@PathVariable Long id) {
		logger.info("Called findDateHiredByPersonId(id)");
		return personRepository.findById(id).get().getDateHired();
	}

	public Date findBirthDayByPersonId(@PathVariable Long id) {
		logger.info("Called findBirthDayByPersonId(id)");
		return personRepository.findById(id).get().getbDay();
	}


	public PersonDTO updatePerson(PersonDTO newPerson, Long id) {
		logger.info("Called updatePerson(newPerson, id)");
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
		logger.info("Called addRole(id, newRole)");

		return personRepository.findById(id)
			.map(person -> {
				Role addRole = roleMapper.mapToRole(newRole);

				if(roleRepository.existsByRole(addRole.getRole())){
					 addRole = roleRepository.findByRole(newRole.getRole());
					 logger.trace("Role to add = " + addRole);

					 if(!person.getRoles().contains(addRole)){
						 logger.trace("Role persons before adding = " + addRole.getPersons());
						 addRole.getPersons().add(person);
						 logger.trace("Role persons after adding = " + addRole.getPersons());

						 roleRepository.save(addRole);
					 }else{
						 throw new ResourceAlreadyExistsException("Person already has Role: " + newRole);
					 }
				}
				else{
					throw new ResourceNotFoundException("Role not found with Role:" + newRole);
				}

				logger.trace("Person Roles before adding = " + person.getRoles());
				person.getRoles().add(addRole);
				logger.trace("Person Roles after adding = " + person.getRoles());

				return roleMapper.createRoleSetDTO(personRepository.save(person).getRoles());
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
	}

	public List<ContactInfo> findPersonContacts(Long personId) {
		logger.info("Called findPersonContacts(personId)");
		return contactInfoRepository.findByPersonId(personId);
	}

	public Set<Role> findPersonRoles(Long personId) {
		logger.info("Called findPersonRoles(personId)");
		return personRepository.findById(personId).get().getRoles();
	}

	public void deleteById(Long id) {
		logger.info("Called deleteById(Id)");
		personRepository.deleteById(id);
	}

	public ResponseEntity<?> deletePerson(Long personId) {
		logger.info("Called deletePerson(personId)");
		return personRepository.findById(personId)
				.map(person -> {
					personRepository.delete(person);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId));
	}
 }

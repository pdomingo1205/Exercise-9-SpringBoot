package services;

import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.stream.Collectors;
import models.entities.Person;
import models.entities.ContactInfo;
import models.entities.Role;

import models.dto.*;
import models.entities.*;
import models.projection.*;

import repository.PersonRepository;
import repository.RoleRepository;
import repository.ContactInfoRepository;
import exception.ResourceNotFoundException;
import exception.ResourceAlreadyExistsException;
import mappers.PersonMapper;
import mappers.RoleMapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.BoundMapperFacade;

import org.springframework.transaction.annotation.Transactional;
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

	private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

	private BoundMapperFacade<Person, PersonDTO> pMapper =  mapperFactory.getMapperFacade(Person.class, PersonDTO.class);
	private BoundMapperFacade<Address, AddressDTO> aMapper =  mapperFactory.getMapperFacade(Address.class, AddressDTO.class);
	private BoundMapperFacade<ContactInfo, ContactInfoDTO> cMapper =  mapperFactory.getMapperFacade(ContactInfo.class, ContactInfoDTO.class);
	private BoundMapperFacade<Role, RoleDTO> rMapper =  mapperFactory.getMapperFacade(Role.class, RoleDTO.class);

	private MapperFacade mapperFacade = mapperFactory.getMapperFacade();

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	private PersonMapper personMapper = new PersonMapper();
	private RoleMapper roleMapper = new RoleMapper();

	@Transactional
	public void addRoleToAllUsers(String roleName) {

		Role role = roleRepository.findByRole(roleName);

		for (Person person : personRepository.findAll()) {
			person.getRoles().add(role);
			personRepository.save(person);
		}
	}
	public List<PersonDTO> findAll() {
		logger.info("Called findAll()");

		List<PersonDTO> personsDTO = personRepository.findAll().stream()
	 		  .map(person -> pMapper.map(person))
			  .collect(Collectors.toList());

		return personsDTO;
	 }

	public PersonDTO createPerson(PersonDTO newPerson) {
		logger.info("Called createPerson(newPerson)");
		return pMapper.map(personRepository.save(pMapper.mapReverse(newPerson)));
	}

	public PersonDTO findById(Long id) {
		logger.info("Called findById(id)");

		Person person = personRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));

		return pMapper.map(person);
	}

	public List<PersonDTO> findByLastName(String lastName) {
		logger.info("Called findByLastName(lastName)");
		return personRepository.findAllPersonByNameLastName(lastName).stream()
															.map(person -> pMapper.map(person))
															.collect(Collectors.toList());
	}

	public NameDTO findNameById(Long id) {
		logger.info("Called findNameById(id)");
		return mapperFacade.map(personRepository.findById(id).get().getName(), NameDTO.class);
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

		return persons.stream().map(person -> pMapper.map(person)).collect(Collectors.toList());
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
		return persons.stream().map(person -> pMapper.map(person)).collect(Collectors.toList());
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
		return persons.stream().map(person -> pMapper.map(person)).collect(Collectors.toList());
	}

	public Set<RoleDTO> findRolesByPersonId(@PathVariable Long id) {
		logger.info("Called findRolesByPersonId(id)");
		return personRepository.findById(id).get().getRoles().stream()
															.map(role -> rMapper.map(role))
															.collect(Collectors.toSet());
	}

	public AddressDTO findAddressByPersonId(@PathVariable Long id) {
		logger.info("Called findAddressByPersonId(id)");
		return mapperFacade.map(personRepository.findById(id).get().getAddress(), AddressDTO.class);
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
				return pMapper.map(personRepository.save(person));
			})
			 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
	}

	public Set<RoleDTO> addRole(Long id, RoleDTO newRole) {
		logger.info("Called addRole(id, newRole)");

		return personRepository.findById(id)
			.map(person -> {
				Role addRole = rMapper.mapReverse(newRole);

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

				return personRepository.save(person).getRoles().stream().map(role -> rMapper.map(role)).collect(Collectors.toSet());
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

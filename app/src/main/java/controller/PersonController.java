package controllers;

import java.util.List;
import java.util.Set;
import java.util.Date;

import models.dto.PersonDTO;
import models.dto.AddressDTO;
import models.dto.NameDTO;
import models.dto.RoleDTO;

import models.projection.*;

import services.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RestController
class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);


	private PersonService personService;

	public PersonController(PersonService newPersonService){
		personService = newPersonService;
	}

	@GetMapping("/persons")
	List<PersonDTO> getPersons() {
		logger.info("Called getPersons()");

		return personService.findAll();
	}

	//Sorting

	@RequestMapping(value="/persons/lastname", method = RequestMethod.GET)
	List<PersonLastName> getPersonsSortByLastName(
	@RequestParam(value = "order", required = false) String order) {
		logger.info("Called getPersonsSortByLastName(order)");

		return personService.findAllSortBy(order.toUpperCase());
	}

	@RequestMapping(value="/persons/gwa", method = RequestMethod.GET)
	List<PersonGwa> getPersonsSortByGWA(
	            @RequestParam(value = "order", required = false) String order) {

			logger.info("Called getPersonsSortByGWA(order)");
			return personService.findAllSortByGWA(order.toUpperCase());
	}

	@RequestMapping(value="/persons/datehired", method = RequestMethod.GET)
	List<PersonDateHired> getPersonsSortByDateHired(
	            @RequestParam(value = "order", required = false) String order) {

			logger.info("Called getPersonsSortByDateHired(order)");
			return personService.findAllSortByDateHired(order.toUpperCase());
	}

	//Crud Operations
	@PostMapping("/persons/{personId}/roles")
	Set<RoleDTO> addRoleToPerson(@PathVariable Long personId,
								@RequestBody RoleDTO role) {
		logger.info("Called addRoleToPerson(personId, role)");

		return personService.addRole(personId, role);
	}

	@PostMapping("/persons")
    PersonDTO createPerson(@Valid @RequestBody PersonDTO person) {
		logger.info("Called createPerson(person)");
		return personService.createPerson(person);
    }

	@PutMapping("/persons/{id}")
	PersonDTO updatePerson(@RequestBody PersonDTO newPerson, @PathVariable Long id) {
		logger.info("Called updatePerson(person)");
		return personService.updatePerson(newPerson, id);
	}

	@DeleteMapping("/persons/{id}")
	ResponseEntity<?> deletePerson(@PathVariable Long id) {
		logger.info("Called deletePerson(person)");
		return personService.deletePerson(id);
	 }

	//Functions for finding

	@GetMapping("/persons/{id}")
 	PersonDTO findById(@PathVariable Long id) {
 		logger.info("Called findById(id)");
 		return personService.findById(id);
 	}

	 @GetMapping("/persons/{id}/name")
	 NameDTO findNameById(@PathVariable Long id) {
		 logger.info("Called findNameById(id)");
		 return personService.findNameById(id);
	 }

	@RequestMapping(value="/persons/name", method = RequestMethod.GET)
	List<PersonDTO> findPersonsByLastName(
	@RequestParam(value = "lastName", required = false) String lastName) {
		logger.info("Called findNameByLastName(lastName)");
		return personService.findByLastName(lastName);
	}

	@GetMapping("/persons/{id}/address")
	AddressDTO findAddressById(@PathVariable Long id) {
		logger.info("Called findAddressById(id)");
		return personService.findAddressByPersonId(id);
	}

	@GetMapping("/persons/{id}/gwa")
	Double findGWAById(@PathVariable Long id) {
		logger.info("Called findGWAById(id)");
		return personService.findGWAByPersonId(id);
	}

	@GetMapping("/persons/{id}/datehired")
	Date findDateHiredById(@PathVariable Long id) {
		logger.info("Called findDateHiredById(id)");
		return personService.findDateHiredByPersonId(id);
	}

	@GetMapping("/persons/{id}/bday")
	Date findBirthDayById(@PathVariable Long id) {
		logger.info("Called findBirthDayById(id)");
		return personService.findBirthDayByPersonId(id);
	}

	@GetMapping("/persons/{id}/roles")
	Set<PersonRoles> findRolesById(@PathVariable Long id) {
		logger.info("Called findRolesById(id)");
		return personService.findRolesByPersonId(id);
	}
}

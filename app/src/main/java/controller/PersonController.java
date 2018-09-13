package controllers;

import java.util.List;
import java.util.Set;
import java.util.Date;

import models.entities.*;
import models.dto.*;
import models.projection.*;

import repository.*;
import exception.*;
import services.*;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class PersonController {


	private PersonService personService;

	public PersonController(PersonService newPersonService){
		personService = newPersonService;
	}

	@GetMapping("/persons")
	List<PersonDTO> getPersons() {
		return personService.findAll();
	}

	@RequestMapping(value="/persons/lastname", method = RequestMethod.GET)
	List<PersonLastNameDTO> getPersonsSortByLastName(
	@RequestParam(value = "order", required = false) String order) {
		return personService.findAllSortBy(order.toUpperCase());
	}

	@RequestMapping(value="/persons/gwa", method = RequestMethod.GET)
	List<PersonGwaDTO> getPersonsSortByGWA(
	            @RequestParam(value = "order", required = false) String order) {
			return personService.findAllSortByGWA(order.toUpperCase());
	}

	@RequestMapping(value="/persons/datehired", method = RequestMethod.GET)
	List<PersonDateHiredDTO> getPersonsSortByDateHired(
	            @RequestParam(value = "order", required = false) String order) {
			return personService.findAllSortByDateHired(order.toUpperCase());
	}

	@PostMapping("/persons/{personId}/roles")
	Set<RoleDTO> addRole(@PathVariable Long personId,
								@RequestBody RoleDTO role) {

		return personService.addRole(personId, role);
	}

	@PostMapping("/persons")
    PersonDTO createPerson(@Valid @RequestBody PersonDTO person) {
        return personService.createPerson(person);
    }

	@GetMapping("/persons/{id}/name")
	NameDTO findNameById(@PathVariable Long id) {
		return personService.findNameById(id);
	}

	@RequestMapping(value="/persons/name", method = RequestMethod.GET)
	List<PersonDTO> findPersonsByLastName(
	@RequestParam(value = "lastName", required = false) String lastName) {
		return personService.findByLastName(lastName);
	}

	@GetMapping("/persons/{id}/address")
	AddressDTO findAddressById(@PathVariable Long id) {
		return personService.findAddressByPersonId(id);
	}

	@GetMapping("/persons/{id}/gwa")
	Double findGWAById(@PathVariable Long id) {
		return personService.findGWAByPersonId(id);
	}

	@GetMapping("/persons/{id}/datehired")
	Date findDateHiredById(@PathVariable Long id) {
		return personService.findDateHiredByPersonId(id);
	}

	@GetMapping("/persons/{id}/bday")
	Date findBirthDayById(@PathVariable Long id) {
		return personService.findBirthDayByPersonId(id);
	}

	@GetMapping("/persons/{id}/roles")
	Set<PersonRolesDTO> findRolesById(@PathVariable Long id) {
		return personService.findRolesByPersonId(id);
	}

	@GetMapping("/persons/{id}")
	PersonDTO findById(@PathVariable Long id) {
		return personService.findById(id);
	}

	@PutMapping("/persons/{id}")
	PersonDTO updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
		return personService.updatePerson(newPerson, id);
	}

	@DeleteMapping("/persons/{id}")
	void deletePerson(@PathVariable Long id) {
		personService.deletePerson(id);
	 }
}

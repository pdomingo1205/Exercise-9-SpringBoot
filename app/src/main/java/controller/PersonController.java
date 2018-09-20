package controllers;

import java.util.List;
import java.util.Set;
import java.util.Date;

import models.dto.PersonDTO;
import models.dto.ContactInfoDTO;
import models.dto.AddressDTO;
import models.dto.NameDTO;
import models.dto.RoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;

import services.PersonService;
import services.ContactInfoService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RequestMapping(value="/persons")
@RestController
class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);


	private PersonService personService;
	private ContactInfoService contactService;
	List<PersonDTO> persons;
	ResponseEntity<?> response;

	public PersonController(PersonService newPersonService, ContactInfoService newContactService){
		personService = newPersonService;
		contactService = newContactService;
	}

	//Sorting


	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getPersonsSortBy(
		@RequestParam(value = "sortBy", defaultValue = "NONE") String sortBy,
		@RequestParam(value = "order", defaultValue = "ASC") String order,
		@RequestParam(value = "find", defaultValue = "PERSON") String find,
		@RequestParam(value = "id", required = false) Long id) {

			System.out.println("\n\n\n\n" + id);
		if(id == null){
			System.out.println("\n\n\n\n ENTER SORT");
			switch (sortBy.toUpperCase()) {
				case "LASTNAME":
					response = new ResponseEntity<List<PersonDTO>>(personService.findAllSortBy(order.toUpperCase()), HttpStatus.OK);
					break;
				case "GWA":
					response = new ResponseEntity<List<PersonDTO>>(personService.findAllSortByGWA(order.toUpperCase()), HttpStatus.OK);
					break;
				case "DATEHIRED":
					response = new ResponseEntity<List<PersonDTO>>(personService.findAllSortByDateHired(order.toUpperCase()), HttpStatus.OK);
					break;
				default:
					response = new ResponseEntity<List<PersonDTO>>(personService.findAll(), HttpStatus.OK);
					break;

			}
		}else{
			System.out.println("\n\n\n\n ENTER FIND");
			switch (find.toUpperCase()) {
				case "BDAY":
					logger.info("finding Birthday by ID");
					response = new ResponseEntity<Date>(personService.findBirthDayByPersonId(id), HttpStatus.OK);
					break;
				case "ROLES":
					logger.info("finding Roles by ID");
					response = new ResponseEntity<Set<RoleDTO>>(personService.findRolesByPersonId(id), HttpStatus.OK);
					break;
				case "CONTACTS":
					logger.info("finding Contacts by ID");
					response = new ResponseEntity<List<ContactInfoDTO>>(contactService.getContactInfosByPersonId(id), HttpStatus.OK);
					break;
				case "NAME":
					logger.info("finding Contacts by ID");
					response = new ResponseEntity<NameDTO>(personService.findNameById(id), HttpStatus.OK);
					break;
				case "ADDRESS":
					logger.info("finding Address by ID");
					response = new ResponseEntity<AddressDTO>(personService.findAddressByPersonId(id), HttpStatus.OK);
					break;
				case "GWA":
					logger.info("finding GWA by ID");
					response = new ResponseEntity<Double>(personService.findGWAByPersonId(id), HttpStatus.OK);
					break;
				case "DATEHIRED":
					logger.info("finding GWA by ID");
					response = new ResponseEntity<Date>(personService.findDateHiredByPersonId(id), HttpStatus.OK);
					break;
				default:
					logger.info("finding Person by ID");
					response = new ResponseEntity<PersonDTO>(personService.findById(id), HttpStatus.OK);

			}
		}

		return response;
	}

	//Crud Operations
	@PostMapping("/roles")
	ResponseEntity<?> addRoleToPerson(@PathVariable Long personId,
								@RequestBody RoleDTO role) {
		logger.info("Called addRoleToPerson(personId, role)");

		return new ResponseEntity<Set<RoleDTO>>(personService.addRole(personId, role), HttpStatus.OK);
	}


	@PostMapping
    ResponseEntity<?> createPerson(@Valid @RequestBody PersonDTO person) {
		logger.info("Called createPerson(person)");
		return new ResponseEntity<PersonDTO>(personService.createPerson(person), HttpStatus.OK);
    }

	@PutMapping("/{id}")
	ResponseEntity<?> updatePerson(@RequestBody PersonDTO newPerson, @PathVariable Long id) {
		logger.info("Called updatePerson(person)");
		return new ResponseEntity<PersonDTO>(personService.updatePerson(newPerson, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deletePerson(@PathVariable Long id) {
		logger.info("Called deletePerson(person)");
		return personService.deletePerson(id);
	 }

	@DeleteMapping("/{personId}/contacts/{contactId}")
	void removeContactFromPerson(@PathVariable Long id, @PathVariable Long contactId) {
		logger.info("Called removeContactFromPerson(personId, contactId)");

		contactService.deleteContactInfo(id, contactId);
	}

	@PostMapping("/{personId}/contacts")
	public ResponseEntity<?> addContactToPerson(@PathVariable Long personId,
								@RequestBody ContactInfoDTO contactInfo) {
		logger.info("Called addContactToPerson(personId, contactInfo)");
		return new ResponseEntity<ContactInfoDTO>(contactService.addContactInfo(personId, contactInfo), HttpStatus.OK);
	}


}

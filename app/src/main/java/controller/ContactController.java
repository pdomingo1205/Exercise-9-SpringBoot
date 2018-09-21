package controllers;

import java.util.List;

import models.dto.ContactInfoDTO;

import services.ContactInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.http.ResponseEntity;


@RestController
class ContactController {

	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactInfoService contactService;

	@PutMapping("/contacts/{id}")
	ResponseEntity<?> updateContact(@Valid @RequestBody ContactInfoDTO newContact, @PathVariable Long id) {

		logger.info("Called updateContact(contactInfo, id)");

		return new ResponseEntity<ContactInfoDTO>(contactService.updateContactInfo(id, newContact), HttpStatus.OK);
	}

	@GetMapping("/contacts/{id}")
	ResponseEntity<?> findById(@PathVariable Long id) {
		logger.info("Called createContact(id)");
		return new ResponseEntity<ContactInfoDTO>(contactService.findById(id), HttpStatus.OK);
	}

	@GetMapping("/contacts")
	ResponseEntity<?> findAll() {
		logger.info("Called findAll()");

		return new ResponseEntity<List<ContactInfoDTO>>(contactService.findAll(), HttpStatus.OK);
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/contacts/{id}")
	void deleteById(@PathVariable Long id) {
		logger.info("Called deleteById(id)");

		contactService.deleteById(id);
	}

}

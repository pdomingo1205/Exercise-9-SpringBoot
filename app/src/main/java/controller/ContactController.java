package controllers;

import java.util.List;

import models.dto.ContactInfoDTO;

import services.ContactInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
class ContactController {

	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactInfoService contactService;

	@PutMapping("/contacts/{id}")
	ContactInfoDTO updateContact(@Valid @RequestBody ContactInfoDTO newContact, @PathVariable Long id) {

		logger.info("Called updateContact(contactInfo, id)");
		return contactService.updateContactInfo(id, newContact);
	}

	@GetMapping("/contacts")
	List<ContactInfoDTO> findAll() {
		logger.info("Called findAll()");

		return contactService.findAll();
	}

	@DeleteMapping("/contacts/{id}")
	void deleteById(@PathVariable Long id) {
		logger.info("Called deleteById(id)");

		contactService.deleteById(id);
	}

	@GetMapping("/contacts/{id}")
	ContactInfoDTO findById(@PathVariable Long id) {
		logger.info("Called createContact(id)");
		return contactService.findById(id);
	}
}

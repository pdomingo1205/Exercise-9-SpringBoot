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

	@GetMapping("/persons/{personId}/contacts")
	public List<ContactInfoDTO> getContactsByPersonId(@PathVariable Long personId) {
		logger.info("Called getContactsByPersonId(person Id)");

		List<ContactInfoDTO> contacts = contactService.getContactInfosByPersonId(personId);
        return contacts;
    }

	@PostMapping("/persons/{personId}/contacts")
    public ContactInfoDTO createContact(@PathVariable Long personId,
								@RequestBody ContactInfoDTO contactInfo) {
		logger.info("Called createContact(personId, contactInfo)");

        return contactService.addContactInfo(personId, contactInfo);
    }

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

	@DeleteMapping("/persons/{personId}/contacts/{contactId}")
	void deleteById(@PathVariable Long id, @PathVariable Long contactId) {
		logger.info("Called deleteById(personId, contactId)");

		contactService.deleteContactInfo(id, contactId);
	}

	@GetMapping("/contacts/{id}")
	ContactInfoDTO findById(@PathVariable Long id) {
		logger.info("Called createContact(id)");
		return contactService.findById(id);
	}
}

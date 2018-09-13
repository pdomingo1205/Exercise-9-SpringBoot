package controllers;

import java.util.List;

import models.entities.*;
import models.dto.*;
import models.projection.*;

import repository.*;
import exception.*;
import services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
class ContactController {

	@Autowired
	private ContactInfoService contactService;

	@GetMapping("/persons/{personId}/contacts")
	public List<ContactInfo> getContactsByPersonId(@PathVariable Long personId) {
        List<ContactInfo> contacts = contactService.getContactInfosByPersonId(personId);
        return contacts;
    }

	@PostMapping("/persons/{personId}/contacts")
    public ContactInfo createContact(@PathVariable Long personId,
								@RequestBody ContactInfo contactInfo) {

        return contactService.addContactInfo(personId, contactInfo);
    }

	@PutMapping("/contacts/{id}")
	ContactInfo updateContact(@Valid @RequestBody ContactInfo newContact, @PathVariable Long id) {
		return contactService.updateContactInfo(id, newContact);
	}

	@GetMapping("/contacts")
	List<ContactInfo> findAll() {
		return contactService.findAll();
	}

	@GetMapping("/contacts/{id}")
	ContactInfo findById(@PathVariable Long id) {
		return contactService.findById(id);
	}
}

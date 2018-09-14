package controllers;

import java.util.List;

import models.dto.ContactInfoDTO;

import services.ContactInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
class ContactController {

	@Autowired
	private ContactInfoService contactService;

	@GetMapping("/persons/{personId}/contacts")
	public List<ContactInfoDTO> getContactsByPersonId(@PathVariable Long personId) {
        List<ContactInfoDTO> contacts = contactService.getContactInfosByPersonId(personId);
        return contacts;
    }

	@PostMapping("/persons/{personId}/contacts")
    public ContactInfoDTO createContact(@PathVariable Long personId,
								@RequestBody ContactInfoDTO contactInfo) {

        return contactService.addContactInfo(personId, contactInfo);
    }

	@PutMapping("/contacts/{id}")
	ContactInfoDTO updateContact(@Valid @RequestBody ContactInfoDTO newContact, @PathVariable Long id) {
		return contactService.updateContactInfo(id, newContact);
	}

	@GetMapping("/contacts")
	List<ContactInfoDTO> findAll() {
		return contactService.findAll();
	}

	@DeleteMapping("/contacts/{id}")
	void deleteById(@PathVariable Long id) {
		contactService.deleteById(id);
	}

	@DeleteMapping("/persons/{personId}/contacts/{contactId}")
	void deleteById(@PathVariable Long id, @PathVariable Long contactId) {
		contactService.deleteContactInfo(id, contactId);
	}

	@GetMapping("/contacts/{id}")
	ContactInfoDTO findById(@PathVariable Long id) {
		return contactService.findById(id);
	}
}

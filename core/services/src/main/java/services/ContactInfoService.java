package services;

import repository.*;
import exception.*;

import models.entities.ContactInfo;


import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ContactInfoService {

	@Autowired
	private ContactInfoRepository contactInfoRepository;

	@Autowired
	private PersonRepository personRepository;


	public List<ContactInfo> findAll() {
		return contactInfoRepository.findAll();
	}

	public ContactInfo findById(Long contactInfoId) {
		return contactInfoRepository.findById(contactInfoId)
		.orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + contactInfoId));
	}

	public List<ContactInfo> getContactInfosByPersonId(Long personId) {
		return contactInfoRepository.findByPersonId(personId);
	}

	public ContactInfo addContactInfo(Long personId, ContactInfo contactInfo) {
		return personRepository.findById(personId)
				.map(person -> {
					//person.getContactInfo().add(contactInfo);
					contactInfo.setPerson(person);
					return contactInfoRepository.save(contactInfo);
				}).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId));
	}

	public ContactInfo updateContactInfo(Long personId, Long contactInfoId, ContactInfo contactInfoRequest) {
		if(!personRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person not found with id " + personId);
		}

		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfo.setContactType(contactInfoRequest.getContactType());
					contactInfo.setContactInfo(contactInfoRequest.getContactInfo());
					return contactInfoRepository.save(contactInfo);
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));
	}

	public ContactInfo updateContactInfo(Long contactInfoId, ContactInfo contactInfoRequest) {
		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfo.setContactType(contactInfoRequest.getContactType());
					contactInfo.setContactInfo(contactInfoRequest.getContactInfo());
					return contactInfoRepository.save(contactInfo);
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));
	}

	public ResponseEntity<?> deleteById(Long contactId) {
		return contactInfoRepository.findById(contactId)
				.map(contact -> {
					contactInfoRepository.delete(contact);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("contact not found with id " + contactId));
	}

	public ResponseEntity<?> deleteContactInfo(Long personId, Long contactInfoId) {
		if(!personRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person not found with id " + personId);
		}

		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfoRepository.delete(contactInfo);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));

	}

 }

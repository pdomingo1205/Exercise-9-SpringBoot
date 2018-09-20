package services;

import repository.ContactInfoRepository;
import repository.PersonRepository;
import exception.ResourceNotFoundException;

import models.entities.ContactInfo;
import models.dto.ContactInfoDTO;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
@Service
public class ContactInfoService {

	@Autowired
	private ContactInfoRepository contactInfoRepository;

	@Autowired
	private PersonRepository personRepository;

	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapperFacade = mapperFactory.getMapperFacade();

	private static final Logger logger = LoggerFactory.getLogger(ContactInfoService.class);

	public List<ContactInfoDTO> findAll() {
		return contactInfoRepository.findAll().stream()
											.map(contact ->
											mapperFacade.map(contact, ContactInfoDTO.class)).collect(Collectors.toList());
	}


	public ContactInfoDTO findById(Long id) {
		ContactInfo contactInfo = contactInfoRepository.findById(id)
				 .orElseThrow(() ->
					 new ResourceNotFoundException("Contact not found with id " + id));
		logger.info(String.format("Found %s", contactInfo));

		return mapperFacade.map(contactInfo, ContactInfoDTO.class);
	}


	public List<ContactInfoDTO> getContactInfosByPersonId(Long personId) {

		return contactInfoRepository.findByPersonId(personId)
															.stream()
															.map(contact ->
																mapperFacade.map(contact, ContactInfoDTO.class)).collect(Collectors.toList());
	}

	public ContactInfoDTO addContactInfo(Long personId, ContactInfoDTO contactInfoDTO) {
		return personRepository.findById(personId)
				.map(person -> {
					ContactInfo contactInfo = mapperFacade.map(contactInfoDTO, ContactInfo.class);
					Set<ContactInfo> contacts;

					try{
						contacts = person.getContactInfo();
					}catch(Exception e){
						contacts = new HashSet<ContactInfo>();
					}
					contacts.add(contactInfo);
					person.setContactInfo(contacts);
					contactInfo.setPerson(person);

					logger.info(String.format("Saved %s", contactInfo));
					return mapperFacade.map(contactInfoRepository.save(contactInfo), ContactInfoDTO.class);
				}).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId));
	}

	public ContactInfoDTO updateContactInfo(Long personId, Long contactInfoId, ContactInfoDTO contactInfoRequest) {
		if(!personRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person not found with id " + personId);
		}

		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfo.setContactType(contactInfoRequest.getContactType());
					contactInfo.setContactInfo(contactInfoRequest.getContactInfo());
					logger.info(String.format("Updated %s", contactInfo));
					return mapperFacade.map(contactInfoRepository.save(contactInfo), ContactInfoDTO.class);
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));
	}

	public ContactInfoDTO updateContactInfo(Long contactInfoId, ContactInfoDTO contactInfoRequest) {
		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfo.setContactType(contactInfoRequest.getContactType());
					contactInfo.setContactInfo(contactInfoRequest.getContactInfo());
					logger.info(String.format("Saved %s", contactInfo));
					return mapperFacade.map(contactInfoRepository.save(contactInfo), ContactInfoDTO.class);
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));
	}

	public ResponseEntity<?> deleteById(Long contactId) {
		return contactInfoRepository.findById(contactId)
				.map(contact -> {
					contactInfoRepository.delete(contact);
					logger.info(String.format("Deleted %s", contact));
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
					logger.info(String.format("deleted %s", contactInfo));
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));

	}

 }

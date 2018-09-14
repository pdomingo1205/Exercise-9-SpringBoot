package services;

import repository.ContactInfoRepository;
import repository.PersonRepository;
import exception.ResourceNotFoundException;

import models.entities.ContactInfo;
import models.dto.ContactInfoDTO;
import mappers.ContactInfoMapper;


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

	private ContactInfoMapper contactMapper = new ContactInfoMapper();


	public List<ContactInfoDTO> findAll() {
		return contactMapper.mapToContactInfoDTOList(contactInfoRepository.findAll());
	}


	public ContactInfoDTO findById(Long id) {
		ContactInfo contactInfo = contactInfoRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + id));
		return contactMapper.mapToContactInfoDTO(contactInfo);
	}


	public List<ContactInfoDTO> getContactInfosByPersonId(Long personId) {
		return contactMapper.mapToContactInfoDTOList(contactInfoRepository.findByPersonId(personId));
	}

	public ContactInfoDTO addContactInfo(Long personId, ContactInfoDTO contactInfoDTO) {
		return personRepository.findById(personId)
				.map(person -> {
					ContactInfo contactInfo = contactMapper.mapToContactInfo(contactInfoDTO);
					contactInfo.setPerson(person);
					return contactMapper.mapToContactInfoDTO(contactInfoRepository.save(contactInfo));
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
					return contactMapper.mapToContactInfoDTO(contactInfoRepository.save(contactInfo));
				}).orElseThrow(() -> new ResourceNotFoundException("ContactInfo not found with id " + contactInfoId));
	}

	public ContactInfoDTO updateContactInfo(Long contactInfoId, ContactInfoDTO contactInfoRequest) {
		return contactInfoRepository.findById(contactInfoId)
				.map(contactInfo -> {
					contactInfo.setContactType(contactInfoRequest.getContactType());
					contactInfo.setContactInfo(contactInfoRequest.getContactInfo());
					return contactMapper.mapToContactInfoDTO(contactInfoRepository.save(contactInfo));
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

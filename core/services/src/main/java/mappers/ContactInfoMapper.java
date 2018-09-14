package mappers;


import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import models.entities.Person;
import models.entities.ContactInfo;

import models.dto.ContactInfoDTO;
import models.dto.PersonDTO;
import models.projection.*;


import javax.validation.Valid;


public class ContactInfoMapper {


	public ContactInfoMapper(){
	}

	public Set<ContactInfo> createContactInfoSet(Set<ContactInfoDTO> contactInfosDTO) {
        Set<ContactInfo> contactInfos = new HashSet<>();
        contactInfosDTO.forEach(contactInfo -> contactInfos.add(mapToContactInfo(contactInfo)));
        return contactInfos;
    }

	public Set<ContactInfoDTO> createContactInfoSetDTO(Set<ContactInfo> contactInfos) {
		Set<ContactInfoDTO> contactInfosDTO = new HashSet<>();
		contactInfos.forEach(contactInfo -> contactInfosDTO.add(mapToContactInfoDTO(contactInfo)));
		return contactInfosDTO;
	}

	public ContactInfoDTO mapToContactInfoDTO(ContactInfo contactInfo){
		ContactInfoDTO contactInfoDTO = new ContactInfoDTO();

		contactInfoDTO.setContactInfoId(contactInfo.getContactInfoId());
		contactInfoDTO.setContactInfo(contactInfo.getContactInfo());
		contactInfoDTO.setContactType(contactInfo.getContactType());

		return contactInfoDTO;
	}

	public ContactInfo mapToContactInfo(ContactInfoDTO contactInfoDTO) {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setContactInfoId(contactInfoDTO.getContactInfoId());
		contactInfo.setContactInfo(contactInfoDTO.getContactInfo());
		contactInfo.setContactType(contactInfoDTO.getContactType());

		return contactInfo;
	}

	public List<ContactInfoDTO> mapToContactInfoDTOList(List<ContactInfo> contactInfos){
		List<ContactInfoDTO> contactInfoDTOs = new ArrayList<ContactInfoDTO>();
		contactInfos.stream().forEach(contactInfo -> contactInfoDTOs.add(mapToContactInfoDTO(contactInfo)));

		return contactInfoDTOs;
	}



}

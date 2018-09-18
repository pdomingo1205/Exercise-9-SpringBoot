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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactInfoMapper {

	private static final Logger logger = LoggerFactory.getLogger(ContactInfoMapper.class);

	public ContactInfoMapper(){
	}

	public Set<ContactInfo> createContactInfoSet(Set<ContactInfoDTO> contactInfosDTO) {
		logger.info("Called createContactInfoSet(contactInfosDto)");

        Set<ContactInfo> contactInfos = new HashSet<>();
        contactInfosDTO.forEach(contactInfo -> contactInfos.add(mapToContactInfo(contactInfo)));
		logger.debug(String.format("contactInfos value = %s", contactInfos));
		logger.debug(String.format("contactInfosDTO value = %s", contactInfosDTO));

        return contactInfos;
    }

	public Set<ContactInfoDTO> createContactInfoSetDTO(Set<ContactInfo> contactInfos) {
		logger.info("Called createContactInfoSetDTO(contactInfos)");

		Set<ContactInfoDTO> contactInfosDTO = new HashSet<>();
		contactInfos.forEach(contactInfo -> contactInfosDTO.add(mapToContactInfoDTO(contactInfo)));
		logger.debug(String.format("contactInfos value = %s", contactInfos));
		logger.debug(String.format("contactInfosDTO value = %s", contactInfosDTO));

		return contactInfosDTO;
	}

	public ContactInfoDTO mapToContactInfoDTO(ContactInfo contactInfo){
		logger.info("Called mapToContactInfoDTO(contactInfo)");
		ContactInfoDTO contactInfoDTO = new ContactInfoDTO();

		contactInfoDTO.setContactInfoId(contactInfo.getContactInfoId());
		contactInfoDTO.setContactInfo(contactInfo.getContactInfo());
		contactInfoDTO.setContactType(contactInfo.getContactType());
		logger.debug(String.format("contactInfoDTO value = %s", contactInfoDTO));

		return contactInfoDTO;
	}

	public ContactInfo mapToContactInfo(ContactInfoDTO contactInfoDTO) {
		logger.info("Called mapToContactInfo(contactInfoDTO)");

		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setContactInfoId(contactInfoDTO.getContactInfoId());
		contactInfo.setContactInfo(contactInfoDTO.getContactInfo());
		contactInfo.setContactType(contactInfoDTO.getContactType());
		logger.debug(String.format("contactInfo value = %s", contactInfo));

		return contactInfo;
	}

	public List<ContactInfoDTO> mapToContactInfoDTOList(List<ContactInfo> contactInfos){
		logger.info("Called mapToContactInfoDTOList(contactInfos)");

		List<ContactInfoDTO> contactInfoDTOs = new ArrayList<ContactInfoDTO>();
		contactInfos.stream().forEach(contactInfo -> contactInfoDTOs.add(mapToContactInfoDTO(contactInfo)));
		logger.debug(String.format("contactInfos value = %s", contactInfos));

		return contactInfoDTOs;
	}



}

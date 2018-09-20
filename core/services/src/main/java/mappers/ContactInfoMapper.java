package mappers;


import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import models.entities.Person;
import models.entities.ContactInfo;

import models.dto.ContactInfoDTO;
import models.dto.PersonDTO;
import models.projection.*;


import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ContactInfoMapper {

	private static final Logger logger = LoggerFactory.getLogger(ContactInfoMapper.class);

	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapperFacade = mapperFactory.getMapperFacade();

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
		return mapperFacade.map(contactInfoDTO,ContactInfo.class);
	}

	public List<ContactInfoDTO> mapToContactInfoDTOList(List<ContactInfo> contactInfos){
		logger.info("Called mapToContactInfoDTOList(contactInfos)");

		List<ContactInfoDTO> contactInfoDTOs = contactInfos.stream()
	 			   							  .map(contact -> mapperFacade.map(contact,ContactInfoDTO.class))
											  .collect(Collectors.toList());

		logger.debug(String.format("contactInfos value = %s", contactInfos));

		return contactInfoDTOs;
	}



}

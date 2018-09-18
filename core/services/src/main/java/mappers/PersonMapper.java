package mappers;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import models.entities.Person;
import models.entities.Name;
import models.entities.Address;

import models.dto.PersonDTO;
import models.dto.AddressDTO;
import models.dto.NameDTO;

import models.projection.*;

import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class PersonMapper {

	private static final Logger logger = LoggerFactory.getLogger(PersonMapper.class);

	public PersonMapper(){
	}


	public AddressDTO mapToAddressDTO(Address address){
		logger.info("Called mapToAddressDTO(address)");

		AddressDTO addressDTO = new AddressDTO();

		addressDTO.setStreetNo(StringUtils.defaultString(address.getStreetNo()));
		addressDTO.setBarangay(StringUtils.defaultString(address.getBarangay()));
		addressDTO.setMunicipality(StringUtils.defaultString(address.getMunicipality()));
		addressDTO.setZipCode(StringUtils.defaultString(address.getZipCode()));

		logger.debug(String.format("addressDTO value = %s", addressDTO));

		return addressDTO;
	}

	public Address mapToAddress(AddressDTO addressDTO){
		logger.info("Called mapToAddress(addressDTO)");

		Address address = new Address();
		address.setStreetNo(StringUtils.defaultString(addressDTO.getStreetNo()));
		address.setBarangay(StringUtils.defaultString(addressDTO.getBarangay()));
		address.setMunicipality(StringUtils.defaultString(addressDTO.getMunicipality()));
		address.setZipCode(StringUtils.defaultString(addressDTO.getZipCode()));

		logger.debug(String.format("addressDTO value = %s", address));

		return address;
	}

	public Name mapToName(NameDTO nameDTO){
		logger.info("Called mapToName(nameDTO)");

		Name name = new Name();
		name.setTitle(StringUtils.defaultString(nameDTO.getTitle()));
		name.setFirstName(StringUtils.defaultString(nameDTO.getFirstName()));
		name.setMiddleName(StringUtils.defaultString(nameDTO.getMiddleName()));
		name.setLastName(StringUtils.defaultString(nameDTO.getLastName()));
		name.setSuffix(StringUtils.defaultString(nameDTO.getSuffix()));

		logger.debug(String.format("name value = %s", name));

		return name;
	}

	public NameDTO mapToNameDTO(Name name){
		logger.info("Called mapToNameDTO(name)");

		NameDTO nameDTO = new NameDTO();

		nameDTO.setTitle(StringUtils.defaultString(name.getTitle()));
		nameDTO.setFirstName(StringUtils.defaultString(name.getFirstName()));
		nameDTO.setMiddleName(StringUtils.defaultString(name.getMiddleName()));
		nameDTO.setLastName(StringUtils.defaultString(name.getLastName()));
		nameDTO.setSuffix(StringUtils.defaultString(name.getSuffix()));

		logger.debug(String.format("nameDTO value = %s", nameDTO));

		return nameDTO;
	}

	public Person mapToPerson(PersonDTO personDTO) {
		logger.info("Called mapToPerson(personDTO)");

		Person person = new Person();

		person.setId(personDTO.getPersonId());
		person.setName(mapToName(personDTO.getName()));
		person.setbDay(personDTO.getbDay());
		person.setGWA(personDTO.getGWA());
		person.setAddress(mapToAddress(personDTO.getAddress()));
		person.setCurrEmployed(personDTO.getCurrEmployed());
		person.setDateHired(personDTO.getDateHired());

		logger.debug(String.format("person value = %s", person));

		return person;
	}

	public PersonDTO mapToPersonDTO(Person person) {
		logger.info("Called mapToPersonDTO(person)");

		PersonDTO personDTO = new PersonDTO();

        personDTO.setPersonId(person.getId());
		personDTO.setName(mapToNameDTO(person.getName()));
		personDTO.setbDay(person.getbDay());
		personDTO.setGWA(person.getGWA());
		personDTO.setAddress(mapToAddressDTO(person.getAddress()));
		personDTO.setCurrEmployed(person.getCurrEmployed());
		personDTO.setDateHired(person.getDateHired());

		logger.debug(String.format("personDTO value = %s", personDTO));

		return personDTO;
	}

	public List<PersonLastName> sortLastNameDTO(List<Person> persons) {
		logger.info("Called sortLastNameDTO(persons)");
		List<PersonLastName> personsDTO = new ArrayList<PersonLastName>();
		persons.forEach(person -> {
			PersonLastName personDTO = new PersonLastName();
			personDTO.setPersonId(person.getId());
			NameDTO name = mapToNameDTO(person.getName());
			name.setLastName(name.getLastName().toUpperCase().toString());
			personDTO.setName(name);

			personsDTO.add(personDTO);
		});
		logger.debug(String.format("persons value = %s\n", persons));
		logger.debug(String.format("personDTO value = %s\n", personsDTO));

		return personsDTO;
	}

	public List<PersonGwa> sortGwaDTO(List<Person> persons) {
		logger.info("Called sortGwaDTO(persons)");

		List<PersonGwa> personsDTO = new ArrayList<PersonGwa>();
		persons.forEach(person -> {
			PersonGwa personDTO = new PersonGwa();
			personDTO.setPersonId(person.getId());
			personDTO.setName(mapToNameDTO(person.getName()));
			personDTO.setGWA(person.getGWA());

			personsDTO.add(personDTO);
		});
		logger.debug(String.format("persons value = %s\n", persons));
		logger.debug(String.format("personsDTO value = %s\n", personsDTO));

		return personsDTO;
	}

	public List<PersonDateHired> sortDateHiredDTO(List<Person> persons) {
		logger.info("Called sortDateHiredDTO(persons)");

		List<PersonDateHired> personsDTO = new ArrayList<PersonDateHired>();
		persons.forEach(person -> {
			PersonDateHired personDTO = new PersonDateHired();
			personDTO.setPersonId(person.getId());
			personDTO.setName(mapToNameDTO(person.getName()));
			personDTO.setDateHired(person.getDateHired());

			personsDTO.add(personDTO);
		});
		logger.debug(String.format("persons value = %s\n", persons));
		logger.debug(String.format("personsDTO value = %s\n", personsDTO));

		return personsDTO;
	}

	public List<PersonDTO> mapToPersonDTOList(List<Person> persons){
		logger.info("Called mapToPersonDTOList(persons)");

		List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
		persons.stream().forEach(person -> personDTOs.add(mapToPersonDTO(person)));

		logger.debug(String.format("persons value = %s\n", persons));
		logger.debug(String.format("personsDTO value = %s\n", personDTOs));

		return personDTOs;
	}

}

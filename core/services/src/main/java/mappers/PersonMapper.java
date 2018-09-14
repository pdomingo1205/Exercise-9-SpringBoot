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


public class PersonMapper {


	public PersonMapper(){
	}


	public AddressDTO mapToAddressDTO(Address address){
		AddressDTO addressDTO = new AddressDTO();

		addressDTO.setStreetNo(address.getStreetNo());
		addressDTO.setBarangay(address.getBarangay());
		addressDTO.setMunicipality(address.getMunicipality());
		addressDTO.setZipCode(address.getZipCode());

		return addressDTO;
	}

	public Address mapToAddress(AddressDTO addressDTO){
		Address address = new Address();
		address.setStreetNo(addressDTO.getStreetNo());
		address.setBarangay(addressDTO.getBarangay());
		address.setMunicipality(addressDTO.getMunicipality());
		address.setZipCode(addressDTO.getZipCode());

		return address;
	}

	public Name mapToName(NameDTO nameDTO){
		Name name = new Name();
		name.setTitle(nameDTO.getTitle());
		name.setFirstName(nameDTO.getFirstName());
		name.setMiddleName(nameDTO.getMiddleName());
		name.setLastName(nameDTO.getLastName());
		name.setSuffix(nameDTO.getSuffix());

		return name;
	}

	public NameDTO mapToNameDTO(Name name){
		NameDTO nameDTO = new NameDTO();
		nameDTO.setTitle(name.getTitle());
		nameDTO.setFirstName(name.getFirstName());
		nameDTO.setMiddleName(name.getMiddleName());
		nameDTO.setLastName(name.getLastName());
		nameDTO.setSuffix(name.getSuffix());

		return nameDTO;
	}

	public Person mapToPerson(PersonDTO personDTO) {
		Person person = new Person();

		person.setId(personDTO.getPersonId());
		person.setName(mapToName(personDTO.getName()));
		person.setbDay(personDTO.getbDay());
		person.setGWA(personDTO.getGWA());
		person.setAddress(mapToAddress(personDTO.getAddress()));
		person.setCurrEmployed(personDTO.getCurrEmployed());
		person.setDateHired(personDTO.getDateHired());

		return person;
	}

	public PersonDTO mapToPersonDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();

        personDTO.setPersonId(person.getId());
		personDTO.setName(mapToNameDTO(person.getName()));
		personDTO.setbDay(person.getbDay());
		personDTO.setGWA(person.getGWA());
		personDTO.setAddress(mapToAddressDTO(person.getAddress()));
		personDTO.setCurrEmployed(person.getCurrEmployed());
		personDTO.setDateHired(person.getDateHired());

		return personDTO;
	}

	public List<PersonLastName> sortLastNameDTO(List<Person> persons) {
		List<PersonLastName> personsDTO = new ArrayList<PersonLastName>();
		persons.forEach(person -> {
			PersonLastName personDTO = new PersonLastName();
			personDTO.setPersonId(person.getId());
			NameDTO name = mapToNameDTO(person.getName());
			name.setLastName(name.getLastName().toUpperCase().toString());
			personDTO.setName(name);

			personsDTO.add(personDTO);
		});
		return personsDTO;
	}

	public List<PersonGwa> sortGwaDTO(List<Person> persons) {
		List<PersonGwa> personsDTO = new ArrayList<PersonGwa>();
		persons.forEach(person -> {
			PersonGwa personDTO = new PersonGwa();
			personDTO.setPersonId(person.getId());
			personDTO.setName(mapToNameDTO(person.getName()));
			personDTO.setGWA(person.getGWA());

			personsDTO.add(personDTO);
		});
		return personsDTO;
	}

	public List<PersonDateHired> sortDateHiredDTO(List<Person> persons) {
		List<PersonDateHired> personsDTO = new ArrayList<PersonDateHired>();
		persons.forEach(person -> {
			PersonDateHired personDTO = new PersonDateHired();
			personDTO.setPersonId(person.getId());
			personDTO.setName(mapToNameDTO(person.getName()));
			personDTO.setDateHired(person.getDateHired());

			personsDTO.add(personDTO);
		});
		return personsDTO;
	}


	public List<PersonDTO> mapToPersonDTOList(List<Person> persons){
		List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();

		persons.stream().forEach(person -> personDTOs.add(mapToPersonDTO(person)));

		System.out.println(personDTOs);

		return personDTOs;
	}


}

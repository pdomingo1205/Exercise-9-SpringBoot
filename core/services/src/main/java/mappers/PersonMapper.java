package mappers;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import models.entities.*;
import models.dto.*;
import models.projection.*;

import javax.validation.Valid;


public class PersonMapper {


	public PersonMapper(){
	}

	public RoleMapper roleMapper = new RoleMapper();



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

	public List<PersonLastNameDTO> sortLastNameDTO(List<Person> persons) {
		List<PersonLastNameDTO> personsDTO = new ArrayList<PersonLastNameDTO>();
		persons.forEach(person -> {
			PersonLastNameDTO personDTO = new PersonLastNameDTO();
			personDTO.setPersonId(person.getId());
			NameDTO name = mapToNameDTO(person.getName());
			name.setLastName(name.getLastName().toUpperCase().toString());
			personDTO.setName(name);

			personsDTO.add(personDTO);
		});
		return personsDTO;
	}

	public List<PersonGwaDTO> sortGwaDTO(List<Person> persons) {
		List<PersonGwaDTO> personsDTO = new ArrayList<PersonGwaDTO>();
		persons.forEach(person -> {
			PersonGwaDTO personDTO = new PersonGwaDTO();
			personDTO.setPersonId(person.getId());
			personDTO.setName(mapToNameDTO(person.getName()));
			personDTO.setGWA(person.getGWA());

			personsDTO.add(personDTO);
		});
		return personsDTO;
	}

	public List<PersonDateHiredDTO> sortDateHiredDTO(List<Person> persons) {
		List<PersonDateHiredDTO> personsDTO = new ArrayList<PersonDateHiredDTO>();
		persons.forEach(person -> {
			PersonDateHiredDTO personDTO = new PersonDateHiredDTO();
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

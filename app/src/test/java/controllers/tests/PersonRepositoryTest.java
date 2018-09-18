package controllers.tests;

import app.Application;
import repository.PersonRepository;
import models.entities.Person;
import models.entities.Name;
import models.projection.PersonLastName;
import exception.ResourceNotFoundException;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.After;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
public class PersonRepositoryTest {


	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private PersonRepository personRepository;

	@After
	public void cleanUp(){
		List<Person> persons = personRepository.findAll();

		persons.stream().forEach(person -> testEntityManager.remove(person));
	}

	@Test
	public void test_personFindById_true() {
		Person person = new Person();

		testEntityManager.persist(person);
	   	testEntityManager.flush();

		Person foundPerson = personRepository.findById(person.getId())
	 					 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + person.getId()));

		assertEquals(foundPerson.getId(), person.getId());
	}

	@Test
	public void test_personFindById_false() {
		Person person = new Person();

		testEntityManager.persist(person);
	   	testEntityManager.flush();

		Person foundPerson = personRepository.findById(person.getId())
	 					 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + person.getId()));

		assertNotEquals(Long.valueOf(-5), person.getId());
	}

	@Test(expected = RuntimeException.class)
	public void test_personDelete_true() {
		Person person = new Person();

		testEntityManager.persist(person);
		testEntityManager.remove(person);
	   	testEntityManager.flush();

			personRepository.findById(person.getId())
		 					 .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + person.getId()));
	}

	@Test
	public void test_personUpdate_true() {
		Person person = new Person();

		testEntityManager.persist(person);
		assertNotEquals(Double.valueOf(1.5), personRepository.findById(person.getId()).get().getGWA());
		person.setGWA(Double.valueOf(1.5));
		testEntityManager.merge(person);
	   	testEntityManager.flush();
		assertEquals(Double.valueOf(1.5), personRepository.findById(person.getId()).get().getGWA());

	}

	@Test
	public void test_personUpdate_false() {
		Person person = new Person();

		testEntityManager.persist(person);
	   	testEntityManager.flush();
		assertNotEquals(Double.valueOf(1.5), personRepository.findById(person.getId()).get().getGWA());

	}

	@Test
	public void test_findAllByLastName_true() {
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();

		Name name = new Name();

		person = new Person();
		name.setLastName("Villareal");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Calabroso");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Fumera");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		testEntityManager.flush();

		List<Person> repoPersons = personRepository.findAllByOrderByNameLastNameAsc();

		assertEquals(repoPersons.get(0).getName().getLastName(), "Calabroso");
		assertEquals(repoPersons.get(1).getName().getLastName(), "Fumera");
		assertEquals(repoPersons.get(2).getName().getLastName(), "Villareal");

	}

	@Test
	public void test_findAllByLastName_false() {
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();

		Name name = new Name();

		person = new Person();
		name.setLastName("Villareal");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Calabroso");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Fumera");
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		testEntityManager.flush();

		List<Person> repoPersons = personRepository.findAllByOrderByNameLastNameAsc();

		assertNotEquals(repoPersons.get(2).getName().getLastName(), "Calabroso");
		assertNotEquals(repoPersons.get(0).getName().getLastName(), "Fumera");
		assertNotEquals(repoPersons.get(1).getName().getLastName(), "Villareal");

	}

	@Test
	public void test_findAllByGWA_true() {
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();

		Name name = new Name();

		person = new Person();
		name.setLastName("Villareal");
		person.setGWA(Double.valueOf(1.75));
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Calabroso");
		person.setGWA(Double.valueOf(2.0));
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		person = new Person();
		name = new Name();
		name.setLastName("Fumera");
		person.setGWA(Double.valueOf(1.25));
		person.setName(name);
		persons.add(person);
		testEntityManager.persist(person);

		testEntityManager.flush();

		List<Person> repoPersons = personRepository.findAllByOrderByGWAAsc();
		System.out.println("\n\n\n" + repoPersons);
		assertEquals(repoPersons.get(0).getName().getLastName(), "Fumera");
		assertEquals(repoPersons.get(1).getName().getLastName(), "Villareal");
		assertEquals(repoPersons.get(2).getName().getLastName(), "Calabroso");

	}


}

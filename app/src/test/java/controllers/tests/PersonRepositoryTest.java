package controllers.tests;

import app.Application;
import repository.PersonRepository;
import models.entities.Person;
import exception.ResourceNotFoundException;
import org.junit.runner.RunWith;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class PersonRepositoryTest {


	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private PersonRepository personRepository;

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


}

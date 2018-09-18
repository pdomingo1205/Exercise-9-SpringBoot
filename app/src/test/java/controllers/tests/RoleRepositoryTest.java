package controllers.tests;

import app.Application;
import repository.RoleRepository;
import models.entities.Role;
import exception.ResourceNotFoundException;
import org.junit.runner.RunWith;
import org.junit.Test;

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
public class RoleRepositoryTest {


	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void test_roleFindById_true() {
		Role role = new Role();

		testEntityManager.persist(role);
	   	testEntityManager.flush();

		Role foundRole = roleRepository.findById(role.getRoleId())
	 					 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + role.getRoleId()));

		assertEquals(foundRole.getRoleId(), role.getRoleId());
	}

	@Test
	public void test_roleFindById_false() {
		Role role = new Role();

		testEntityManager.persist(role);
	   	testEntityManager.flush();

		Role foundRole = roleRepository.findById(role.getRoleId())
	 					 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + role.getRoleId()));

		assertNotEquals(Long.valueOf(-5), role.getRoleId());
	}

	@Test(expected = RuntimeException.class)
	public void test_roleDelete_true() {
		Role role = new Role();

		testEntityManager.persist(role);
		testEntityManager.remove(role);
	   	testEntityManager.flush();

			roleRepository.findById(role.getRoleId())
		 					 .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + role.getRoleId()));
	}

	@Test
	public void test_roleUpdate_true() {
		Role role = new Role();
		role.setRole("Dev");
		testEntityManager.persist(role);
		assertEquals("Dev", roleRepository.findById(role.getRoleId()).get().getRole());
		role.setRole("Developer");
		testEntityManager.merge(role);
	   	testEntityManager.flush();
		assertEquals("Developer", roleRepository.findById(role.getRoleId()).get().getRole());

	}

	@Test
	public void test_roleUpdate_false() {
		Role role = new Role();

		testEntityManager.persist(role);
	   	testEntityManager.flush();
		assertNotEquals("Dev", roleRepository.findById(role.getRoleId()).get().getRole());

	}


}

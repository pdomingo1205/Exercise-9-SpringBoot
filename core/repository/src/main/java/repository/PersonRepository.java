package repository;

import java.util.List;
import java.util.Set;

import models.dto.*;
import models.entities.*;
import models.projection.*;
import repository.*;
import exception.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findByRolesIn(Set<Person> myField);

    List<Person> findAllByOrderByNameLastNameAsc();
    List<Person> findAllByOrderByNameLastNameDesc();

    List<Person> findAllByOrderByGWAAsc();
    List<Person> findAllByOrderByGWADesc();

    List<Person> findAllByOrderByDateHiredAsc();
    List<Person> findAllByOrderByDateHiredDesc();


    List<Person> findAllPersonByNameLastName(String lastName);

 }

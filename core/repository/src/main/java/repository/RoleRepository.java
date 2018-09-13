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

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByPersons(Set<Person> persons);
    Role findByRole(String role);
    boolean existsByRole(String role);

}

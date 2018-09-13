package repository;

import java.util.List;

import models.dto.*;
import models.entities.*;
import models.projection.*;
import repository.*;
import exception.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo,Long> {
    List<ContactInfo> findByPersonId(Long personId);
 }

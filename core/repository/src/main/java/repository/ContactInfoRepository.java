package repository;

import java.util.List;

import models.entities.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo,Long> {
    List<ContactInfo> findByPersonId(Long personId);
 }

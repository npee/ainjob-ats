package com.ainjob.ats.repository;

import com.ainjob.ats.model.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantJpaRepository extends JpaRepository<Applicant, Long> {
}

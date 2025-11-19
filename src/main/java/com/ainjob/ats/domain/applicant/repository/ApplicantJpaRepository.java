package com.ainjob.ats.domain.applicant.repository;

import com.ainjob.ats.domain.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantJpaRepository extends JpaRepository<Applicant, Long> {
}

package com.ainjob.ats.service;

import com.ainjob.ats.model.entity.Applicant;
import com.ainjob.ats.model.response.ApplicantResponse;
import com.ainjob.ats.repository.ApplicantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantJpaRepository applicantJpaRepository;

    public ApplicantResponse getApplicantById(Long id) {
        Applicant applicant = applicantJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Applicant not found"));
        return ApplicantResponse.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .email(applicant.getEmail())
                .careerYears(applicant.getCareerYears())
                .build();

    }
}

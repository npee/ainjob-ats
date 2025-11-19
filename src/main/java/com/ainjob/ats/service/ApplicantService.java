package com.ainjob.ats.service;

import com.ainjob.ats.model.entity.Applicant;
import com.ainjob.ats.model.entity.dto.ApplicantProjection;
import com.ainjob.ats.model.entity.dto.ApplicantSearch;
import com.ainjob.ats.model.response.ApplicantPagedResponse;
import com.ainjob.ats.model.response.ApplicantResponse;
import com.ainjob.ats.repository.ApplicantJpaRepository;
import com.ainjob.ats.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
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

    public ApplicantPagedResponse getApplicantBy(ApplicantSearch search) {
        Page<ApplicantProjection> pages = applicantRepository.findApplicantsBy(search);
        List<ApplicantResponse> contents = pages.stream()
                .map(proj -> ApplicantResponse.builder()
                        .id(proj.getId())
                        .name(proj.getName())
                        .email(proj.getEmail())
                        .careerYears(proj.getCareerYears())
                        .education(proj.getHighestEducationLevel().getDescription())
                        .major(proj.getMajor())
                        .skills(proj.getSkills())
                        .status(proj.getStatus().getDescription())
                        .build())
                .toList();

        return new ApplicantPagedResponse(contents, pages.getNumber(), pages.getSize(), pages.getTotalElements());
    }
}

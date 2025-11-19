package com.ainjob.ats.service;

import com.ainjob.ats.model.entity.dto.ApplicantProjection;
import com.ainjob.ats.model.entity.dto.ApplicantSearch;
import com.ainjob.ats.model.enumerate.ProcessStatus;
import com.ainjob.ats.model.response.ApplicantPagedResponse;
import com.ainjob.ats.model.response.ApplicantResponse;
import com.ainjob.ats.model.response.ApplicantUpdateResponse;
import com.ainjob.ats.repository.ApplicantJpaRepository;
import com.ainjob.ats.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

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

    public ApplicantUpdateResponse updateApplicant(Long applicantId, String from, String to) {
        ProcessStatus previousStatus = ProcessStatus.fromDescription(from);
        ProcessStatus newStatus = ProcessStatus.fromDescription(to);

        if (previousStatus == null || newStatus == null) {
            throw new IllegalArgumentException("잘못된 진행상태 값입니다.");
        }

        applicantRepository.updateApplicantStatus(applicantId, previousStatus, newStatus);

        return new ApplicantUpdateResponse(applicantId, newStatus.getDescription(), LocalDateTime.now());
    }
}

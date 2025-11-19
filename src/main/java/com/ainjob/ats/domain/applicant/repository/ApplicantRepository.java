package com.ainjob.ats.domain.applicant.repository;

import com.ainjob.ats.domain.applicant.dto.projection.ApplicantProjection;
import com.ainjob.ats.domain.applicant.dto.request.ApplicantSearch;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import org.springframework.data.domain.Page;

public interface ApplicantRepository {

    Page<ApplicantProjection> findApplicantsBy(ApplicantSearch search);

    void updateApplicantStatus(Long applicantId, ProcessStatus from, ProcessStatus to);
}

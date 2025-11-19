package com.ainjob.ats.repository;

import com.ainjob.ats.model.entity.dto.ApplicantProjection;
import com.ainjob.ats.model.entity.dto.ApplicantSearch;
import com.ainjob.ats.model.enumerate.ProcessStatus;
import org.springframework.data.domain.Page;

public interface ApplicantRepository {

    Page<ApplicantProjection> findApplicantsBy(ApplicantSearch search);

    void updateApplicantStatus(Long applicantId, ProcessStatus from, ProcessStatus to);
}

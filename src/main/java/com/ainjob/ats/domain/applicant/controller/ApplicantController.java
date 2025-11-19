package com.ainjob.ats.domain.applicant.controller;

import com.ainjob.ats.domain.applicant.dto.request.ApplicantSearch;
import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import com.ainjob.ats.domain.applicant.dto.request.ApplicantStatusUpdateRequest;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantPagedResponse;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantUpdateResponse;
import com.ainjob.ats.domain.applicant.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
@Slf4j
public class ApplicantController {

    private final ApplicantService applicantService;

    /**
     * 지원자 목록 조회
     * @Param education
     * @Param major
     * @Param careerYears (ex: "3-5", "5-10")
     * @Param skills (ex: "java,springboot")
     * @Param status (optional) (ex: 서류합격)
     * @return ApplicantPagedResponse
     */
    @GetMapping
    public ApplicantPagedResponse getApplicants(
            @RequestParam String education,
            @RequestParam String major,
            @RequestParam String careerYears,
            @RequestParam String skills,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 3) Pageable pageable
    ) {
        ApplicantSearch search = new ApplicantSearch(
                Degree.fromDescription(education),
                major,
                careerYears,
                List.of(skills.split(",")),
                ProcessStatus.fromDescription(status),
                pageable
        );
        return applicantService.getApplicantBy(search);
    }

    /**
     * 지원자 상태 변경
     * @Param applicantId
     * @Param from
     * @Param to
     * @return String
     */
    @PatchMapping("/{id}/status")
    public ApplicantUpdateResponse updateApplicantStatus(
        @PathVariable("id") Long applicantId,
        @RequestBody ApplicantStatusUpdateRequest request
    ) {
        log.info(request.toString());
        return applicantService.updateApplicant(applicantId, request.getFrom(), request.getTo());
    }

}

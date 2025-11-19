package com.ainjob.ats.controller;

import com.ainjob.ats.model.response.ApplicantResponse;
import com.ainjob.ats.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping("/{id}")
    public ApplicantResponse getApplicantById(@PathVariable("id") Long id) {
        return applicantService.getApplicantById(id);
    }

    /**
     * 지원자 목록 조회
     * @Param education
     * @Param major
     * @Param careerYears (ex: "3-5", "5-10")
     * @Param skills (ex: "Java,Spring Boot,AWS")
     * @Param status (optional) (ex: 서류합격)
     * @return ApplicantPagedResponse
     */
    @GetMapping
    public String getApplicants(
        @RequestParam String education,
        @RequestParam String major,
        @RequestParam String careerYears,
        @RequestParam String skills,
        @RequestParam(required = false) String status

    ) {
        return "Filtered applicants";
    }

    /**
     * 지원자 상태 변경
     * @Param applicantId
     * @Param from
     * @Param to
     * @return String
     */
    @PatchMapping("/{id}/status")
    public String updateApplicantStatus(
        @PathVariable("id") Long applicantId,
        @RequestParam String from,
        @RequestParam String to
    ) {
        return "Updated applicant status";
    }

}

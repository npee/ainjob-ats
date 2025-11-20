package com.ainjob.ats.domain.applicant.controller;

import com.ainjob.ats.domain.applicant.dto.request.ApplicantSearch;
import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import com.ainjob.ats.domain.applicant.dto.request.ApplicantStatusUpdateRequest;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantPagedResponse;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantUpdateResponse;
import com.ainjob.ats.domain.applicant.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Applicant API", description = "지원자 검색, 상태 변경 기능")
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
    @Operation(
            summary = "지원자 검색",
            description = "학력, 전공, 경력, 기술스택, 진행 상태 조건으로 지원자를 페이징 조회합니다."
    )
    @GetMapping
    public ApplicantPagedResponse getApplicants(
            @Parameter(description = "최종 학력 (예: 대졸, 석사, 박사)", example = "대졸")
            @RequestParam String education,
            @Parameter(description = "전공명 또는 전공 계열", example = "컴공")
            @RequestParam String major,
            @Parameter(description = "경력 기간 (예: 3-5, 5-10)", example = "3-5")
            @RequestParam String careerYears,
            @Parameter(description = "보유 기술 (쉼표로 구분, 예: java,springboot)", example = "java,springboot")
            @RequestParam String skills,
            @Parameter(description = "지원자 상태 (예: 지원, 서류합격, 면접대기, 합격)", example = "지원")
            @RequestParam(required = false) String status,
            @PageableDefault Pageable pageable
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
    @Operation(
            summary = "지원자 상태 변경",
            description = "특정 지원자의 상태를 변경합니다."
    )
    @PatchMapping("/{id}/status")
    public ApplicantUpdateResponse updateApplicantStatus(
        @PathVariable("id") Long applicantId,
        @RequestBody ApplicantStatusUpdateRequest request
    ) {
        log.info(request.toString());
        return applicantService.updateApplicant(applicantId, request.getFrom(), request.getTo());
    }

}

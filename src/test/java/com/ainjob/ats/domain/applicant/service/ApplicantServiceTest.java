package com.ainjob.ats.domain.applicant.service;

import com.ainjob.ats.domain.applicant.ApplicantTestFactory;
import com.ainjob.ats.domain.applicant.dto.projection.ApplicantProjection;
import com.ainjob.ats.domain.applicant.dto.request.ApplicantSearch;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantPagedResponse;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantResponse;
import com.ainjob.ats.domain.applicant.dto.response.ApplicantUpdateResponse;
import com.ainjob.ats.domain.applicant.repository.ApplicantRepository;
import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

    @Mock
    ApplicantRepository applicantRepository;

    @InjectMocks
    ApplicantService applicantService;

    // 공통 프로젝션 객체들
    static ApplicantProjection applicant1;
    static ApplicantProjection applicant2;

    @BeforeAll
    static void setup() {
        // 전역 설정이 필요한 경우 여기에 작성
        applicant1 = ApplicantTestFactory.createProjection(
                1L,
                "김똘똘",
                "kim@example.com",
                Degree.BD,
                "컴공",
                5,
                ProcessStatus.APPLIED,
                List.of("Java", "Spring Boot")
        );

        applicant2 = ApplicantTestFactory.createProjection(
                2L,
                "이서윤",
                "2win@naver.com",
                Degree.MD,
                "컴공",
                4,
                ProcessStatus.APPLIED,
                List.of("Java", "springboot", "AWS")
        );
    }

    @Test
    @DisplayName("Projection을 Response로 정상 변환하고 페이지 정보도 잘 반환한다")
    void getApplicantBy_success() {
        // given
        ApplicantSearch search = new ApplicantSearch(
                Degree.BD,
                "컴공",
                "3-5",
                List.of("java", "springboot"),
                ProcessStatus.APPLIED,
                PageRequest.of(0, 10)
        );

        Page<ApplicantProjection> page =
                new PageImpl<>(List.of(applicant1, applicant2), search.getPageable(), 2);

        when(applicantRepository.findApplicantsBy(search))
                .thenReturn(page);

        // when
        ApplicantPagedResponse response = applicantService.getApplicantBy(search);

        // then
        assertThat(response.getPage()).isEqualTo(0);
        assertThat(response.getSize()).isEqualTo(10);
        assertThat(response.getTotalElements()).isEqualTo(2);

        assertThat(response.getApplicants()).hasSize(2);

        ApplicantResponse r1 = response.getApplicants().get(0);
        assertThat(r1.getId()).isEqualTo(1L);
        assertThat(r1.getName()).isEqualTo("김똘똘");
        assertThat(r1.getEmail()).isEqualTo("kim@example.com");
        assertThat(r1.getCareerYears()).isEqualTo(5);
        assertThat(r1.getEducation()).isEqualTo(Degree.BD.getDescription());
        assertThat(r1.getMajor()).isEqualTo("컴공");
        assertThat(r1.getSkills()).contains("Java", "Spring Boot");
        assertThat(r1.getStatus()).isEqualTo(ProcessStatus.APPLIED.getDescription());

        verify(applicantRepository, times(1)).findApplicantsBy(search);

    }

    @Test
    @DisplayName("updateApplicant - 유효한 진행상태 변경 요청이면 리포지토리 호출 후 응답을 반환한다")
    void updateApplicant_success() {
        // given
        Long applicantId = applicant1.getId();
        String from = "지원";
        String to = "면접대기";

        ProcessStatus prev = ProcessStatus.fromDescription(from);
        ProcessStatus next = ProcessStatus.fromDescription(to);

        ApplicantUpdateResponse result = applicantService.updateApplicant(applicantId, from, to);

        // then
        verify(applicantRepository, times(1))
                .updateApplicantStatus(applicantId, prev, next);

        assertThat(result.getId()).isEqualTo(applicantId);
        assertThat(result.getStatus()).isEqualTo(next.getDescription());
        assertThat(result.getChangedAt()).isNotNull();
    }

    @Test
    @DisplayName("updateApplicant - 잘못된 진행상태 문자열이면 IllegalArgumentException 발생")
    void updateApplicant_invalidStatus() {
        // given
        Long applicantId = 1L;
        String from = "인터뷰대기";
        String to   = "합격";

        // when & then
        assertThatThrownBy(() -> applicantService.updateApplicant(applicantId, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된");


        verify(applicantRepository, never()).updateApplicantStatus(anyLong(), any(), any());
    }


}
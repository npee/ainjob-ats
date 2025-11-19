package com.ainjob.ats.domain.applicant.entity;

import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.MajorGroupCode;
import jakarta.persistence.*;
import lombok.*;

/**
 * 지원자 전공 Entity
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"applicantEducation", "degree", "majorName", "majorGroupCode"})
public class ApplicantMajor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_education_id", nullable = false)
    private ApplicantEducation applicantEducation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(nullable = false)
    private String majorName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MajorGroupCode majorGroupCode;
}

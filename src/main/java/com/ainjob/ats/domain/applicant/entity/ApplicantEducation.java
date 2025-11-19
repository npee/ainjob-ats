package com.ainjob.ats.domain.applicant.entity;

import com.ainjob.ats.global.enumerate.Degree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지원자 학력 Entity
 */

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantEducation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Degree highestEducationLevel;
}

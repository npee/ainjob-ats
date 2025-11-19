package com.ainjob.ats.model.entity;

import com.ainjob.ats.model.enumerate.SkillType;
import jakarta.persistence.*;
import lombok.*;

/**
 * 지원자 보유 기술 Entity
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"applicantCareer", "name", "type"})
public class ApplicantSkill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_career_id", nullable = false)
    private ApplicantCareer applicantCareer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillType type;
}

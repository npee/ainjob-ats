package com.ainjob.ats.domain.company.entity;

import com.ainjob.ats.global.enumerate.SkillType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채용공고_기술스택 매핑 Entity
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingSkill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillType type;
}

package com.ainjob.ats.domain.ats.entity;

import com.ainjob.ats.domain.applicant.entity.Applicant;
import com.ainjob.ats.domain.applicant.entity.Resume;
import com.ainjob.ats.domain.company.entity.JobPosting;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ATS Entity
 */
@Entity
@Table(name = "application_tracking_system")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ATS {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}

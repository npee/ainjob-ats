package com.ainjob.ats.domain.company.entity;

import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.MajorGroupCode;
import com.ainjob.ats.global.enumerate.PositionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CREATE TABLE `job_posting` (
 * 	`id`	bigint	NOT NULL AUTO_INCREMENT PRIMARY KEY,
 * 	`company_id`	bigint	NOT NULL,
 * 	`position_name`	varchar(255)	NOT NULL,
 * 	`position_type`	varchar(50)	NOT NULL,
 * 	`degree`	varchar(50)	NOT NULL,
 * 	`major_group_code`	varchar(50)	NOT NULL,
 * 	`career`	int	NOT NULL
 * );
 */

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPosting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String positionName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(nullable = false)
    private MajorGroupCode majorGroupCode;

    // TODO: 범위로 변경?
    @Column(nullable = false)
    private Integer career;
}

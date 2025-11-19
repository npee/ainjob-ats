package com.ainjob.ats.domain.company.entity;

import com.ainjob.ats.global.enumerate.IndustryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 기업회원 Entity
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IndustryType industry;

    private String logoUrl;

    @Column(nullable = false)
    private String regNo;
}

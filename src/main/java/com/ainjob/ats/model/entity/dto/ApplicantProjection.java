package com.ainjob.ats.model.entity.dto;

import com.ainjob.ats.model.entity.*;
import com.ainjob.ats.model.enumerate.Degree;
import com.ainjob.ats.model.enumerate.ProcessStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ApplicantProjection {

    private final Long id;
    private final String name;
    private final String email;
    private final Degree highestEducationLevel;
    private final String major;
    private final Integer careerYears;
    private final List<String> skills;
    private final ProcessStatus status;

    @QueryProjection
    public ApplicantProjection(Applicant applicant, ApplicantEducation education, ATS ats, Set<ApplicantMajor> majors, Set<ApplicantSkill> skills) {
        this.id = applicant.getId();
        this.name = applicant.getName();
        this.email = applicant.getEmail();
        this.highestEducationLevel = education.getHighestEducationLevel();
        this.major = majors.stream().filter(f -> f.getDegree() == this.highestEducationLevel)
                .map(ApplicantMajor::getMajorName)
                .findFirst()
                .orElse(null);
        this.careerYears = applicant.getCareerYears();
        this.skills = skills.stream()
                .map(ApplicantSkill::getName)
                .toList();
        this.status = ats.getStatus();
    }
}

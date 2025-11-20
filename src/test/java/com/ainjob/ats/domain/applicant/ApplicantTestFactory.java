package com.ainjob.ats.domain.applicant;

import com.ainjob.ats.domain.applicant.dto.projection.ApplicantProjection;
import com.ainjob.ats.domain.applicant.entity.*;
import com.ainjob.ats.domain.ats.entity.ATS;
import com.ainjob.ats.global.enumerate.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicantTestFactory {

    public static ApplicantProjection createProjection(
            Long id,
            String name,
            String email,
            Degree degree,
            String majorName,
            int careerYears,
            ProcessStatus status,
            List<String> skillNames
    ) {

        Applicant applicant = new Applicant(
                id,
                name,
                email,
                LocalDateTime.of(1995, 1, 1, 0, 0).toLocalDate(),
                careerYears,
                "백엔드개발자"
        );

        ApplicantEducation education = new ApplicantEducation(
                id + 1,
                applicant,
                "서울대학교",
                degree
        );

        ATS ats = new ATS(
                id + 2,
                applicant,
                null,
                null,
                ProcessStatus.APPLIED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        ApplicantMajor major = new ApplicantMajor(
                id + 3,
                education,
                degree,
                majorName,
                MajorGroupCode.IT
        );

        Set<ApplicantMajor> majors = Set.of(major);

        ApplicantCareer career = new ApplicantCareer(
                id + 4,
                applicant,
                "네이버"
        );

        Set<ApplicantSkill> skills = skillNames.stream()
                .map(s -> {
                    ApplicantSkill skill = new ApplicantSkill(
                            id,
                            career,
                            s,
                            SkillType.BACKEND
                    );
                    return skill;
                })
                .collect(Collectors.toSet());

        return new ApplicantProjection(applicant, education, ats, majors, skills);
    }
}
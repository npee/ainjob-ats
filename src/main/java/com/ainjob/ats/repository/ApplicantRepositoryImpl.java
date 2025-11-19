package com.ainjob.ats.repository;

import com.ainjob.ats.model.entity.QATS;
import com.ainjob.ats.model.entity.QApplicantSkill;
import com.ainjob.ats.model.entity.dto.ApplicantProjection;
import com.ainjob.ats.model.entity.dto.ApplicantSearch;
import com.ainjob.ats.model.entity.dto.QApplicantProjection;
import com.ainjob.ats.model.enumerate.ProcessStatus;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ainjob.ats.model.entity.QApplicant.applicant;
import static com.ainjob.ats.model.entity.QApplicantCareer.applicantCareer;
import static com.ainjob.ats.model.entity.QApplicantEducation.applicantEducation;
import static com.ainjob.ats.model.entity.QApplicantMajor.applicantMajor;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ApplicantRepositoryImpl implements ApplicantRepository {

    private final JPAQueryFactory queryFactory;

    QApplicantSkill filterSkill = new QApplicantSkill("filterSkill");
    QApplicantSkill allSkill = new QApplicantSkill("allSkill");
    QATS ats = new QATS("ats");

    @Override
    public Page<ApplicantProjection> findApplicantsBy(ApplicantSearch search) {

        // Query 1. 조건에 맞는 id만 리스팅
        List<Long> ids = queryFactory.select(applicant.id)
                .from(applicant)
                .leftJoin(applicantEducation).on(applicantEducation.applicant.eq(applicant))
                .leftJoin(applicantMajor).on(applicantMajor.applicantEducation.eq(applicantEducation))
                .leftJoin(applicantCareer).on(applicantCareer.applicant.eq(applicant))
                .leftJoin(filterSkill).on(
                        filterSkill.applicantCareer.eq(applicantCareer)
                                .and(search.getSkills() == null ? null : filterSkill.name.in(search.getSkills()))
                )
                .leftJoin(allSkill).on(allSkill.applicantCareer.eq(applicantCareer))
                .leftJoin(ats).on(ats.applicant.eq(applicant))
                .where(
                        search.getStatus() != null ? ats.status.eq(search.getStatus()) : null,
                        (search.getDegrees() != null && !search.getDegrees().isEmpty()) ? applicantEducation.highestEducationLevel.in(search.getDegrees()) : null,
                        search.getMajor() != null ? applicantMajor.majorName.eq(search.getMajor()) : null,
                        (search.getMinCareerYears() != null) ? applicant.careerYears.goe(search.getMinCareerYears()) : null,
                        (search.getMaxCareerYears() != null) ? applicant.careerYears.loe(search.getMaxCareerYears()) : null,
                        (search.getSkills() != null && !search.getSkills().isEmpty()) ? filterSkill.name.in(search.getSkills()) : null
                )
                .groupBy(applicant.id)
                .offset(search.getPageable().getOffset())
                .limit(search.getPageable().getPageSize())
                .fetch();

        // Query 2. id로 필요한 정보 페치
        List<ApplicantProjection> result = queryFactory.from(applicant)
                .leftJoin(applicantEducation).on(applicantEducation.applicant.eq(applicant))
                .leftJoin(applicantMajor).on(applicantMajor.applicantEducation.eq(applicantEducation))
                .leftJoin(applicantCareer).on(applicantCareer.applicant.eq(applicant))
                .leftJoin(filterSkill).on(
                        filterSkill.applicantCareer.eq(applicantCareer)
                                .and(search.getSkills() == null ? null : filterSkill.name.in(search.getSkills()))
                )
                .leftJoin(allSkill).on(allSkill.applicantCareer.eq(applicantCareer))
                .leftJoin(ats).on(ats.applicant.eq(applicant))
                .where(applicant.id.in(ids))
                .transform(
                        GroupBy.groupBy(applicant.id).list(
                                new QApplicantProjection(
                                        applicant,
                                        applicantEducation,
                                        ats,
                                        GroupBy.set(applicantMajor),
                                        GroupBy.set(allSkill)
                                )
                        )
                );

        // Query 3. totalCounts
        Long boxedCount = queryFactory.select(applicant.id.countDistinct())
                .from(applicant)
                .leftJoin(applicantEducation).on(applicantEducation.applicant.eq(applicant))
                .leftJoin(applicantMajor).on(applicantMajor.applicantEducation.eq(applicantEducation))
                .leftJoin(applicantCareer).on(applicantCareer.applicant.eq(applicant))
                .leftJoin(filterSkill).on(
                        filterSkill.applicantCareer.eq(applicantCareer)
                                .and(search.getSkills() == null ? null : filterSkill.name.in(search.getSkills()))
                )
                .leftJoin(allSkill).on(allSkill.applicantCareer.eq(applicantCareer))
                .leftJoin(ats).on(ats.applicant.eq(applicant))
                .where(
                        search.getStatus() != null ? ats.status.eq(search.getStatus()) : null,
                        (search.getDegrees() != null && !search.getDegrees().isEmpty()) ? applicantEducation.highestEducationLevel.in(search.getDegrees()) : null,
                        search.getMajor() != null ? applicantMajor.majorName.eq(search.getMajor()) : null,
                        (search.getMinCareerYears() != null) ? applicant.careerYears.goe(search.getMinCareerYears()) : null,
                        (search.getMaxCareerYears() != null) ? applicant.careerYears.loe(search.getMaxCareerYears()) : null,
                        (search.getSkills() != null && !search.getSkills().isEmpty()) ? filterSkill.name.in(search.getSkills()) : null
                )
                .groupBy(applicant.id)
                .fetchOne();

        long count = Optional.ofNullable(boxedCount).orElse(0L);

        return new PageImpl<>(result, search.getPageable(), count);
    }


    @Override
    @Transactional
    public void updateApplicantStatus(Long applicantId, ProcessStatus from, ProcessStatus to) {
        List<Long> atsIds = queryFactory.select(ats.id)
                .from(ats)
                .where(
                        ats.applicant.id.eq(applicantId),
                        ats.status.eq(from))
                .fetch();

        if (atsIds.isEmpty()) {
            throw new IllegalArgumentException("해당 지원자의 현재 상태가 '" + from.getDescription() + "'이/가 아닙니다.");
        }

        queryFactory.update(ats)
                .set(ats.status, to)
                .where(ats.id.in(atsIds))
                .execute();
    }
}

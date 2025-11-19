package com.ainjob.ats.domain.applicant.dto.request;

import com.ainjob.ats.global.enumerate.Degree;
import com.ainjob.ats.global.enumerate.ProcessStatus;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class ApplicantSearch {

    private List<Degree> degrees;
    private String major;
    private Integer minCareerYears;
    private Integer maxCareerYears;
    private List<String> skills;
    private ProcessStatus status;
    private Pageable pageable;

    public ApplicantSearch(Degree degree, String major, String careerYears, List<String> skills, ProcessStatus status, Pageable pageable) {
        this.degrees = Degree.getHigherOrEqual(degree);
        this.major = major;
        if (careerYears != null && !careerYears.isEmpty()) {
            String[] parts = careerYears.split("-");
            if (parts.length == 2) {
                this.minCareerYears = Integer.parseInt(parts[0]);
                this.maxCareerYears = Integer.parseInt(parts[1]);
            } else if (parts.length == 1) {
                this.minCareerYears = Integer.parseInt(parts[0]);
                this.maxCareerYears = Integer.MAX_VALUE;
            } else {
                this.minCareerYears = 0;
                this.maxCareerYears = Integer.MAX_VALUE;
            }
        }
        this.skills = skills.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
        if (this.skills.size() == 1 && this.skills.get(0).isEmpty()) {
            this.skills = null;
        }
        this.status = status;
        this.pageable = pageable;
    }
}

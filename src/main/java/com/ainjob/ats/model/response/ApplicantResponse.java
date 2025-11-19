package com.ainjob.ats.model.response;

import com.ainjob.ats.model.enumerate.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * ApplicantResponse paged contents
 */
@Getter
@AllArgsConstructor
@Builder
public class ApplicantResponse {
    private Long id;
    private String name;
    private String email;
    private String education;
    private String major;
    private Integer careerYears;
    private List<String> skills;
    private ProcessStatus status;
}

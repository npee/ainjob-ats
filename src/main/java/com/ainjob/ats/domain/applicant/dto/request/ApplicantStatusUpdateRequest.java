package com.ainjob.ats.domain.applicant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ApplicantStatusUpdateRequest {
    private String from;
    private String to;
}

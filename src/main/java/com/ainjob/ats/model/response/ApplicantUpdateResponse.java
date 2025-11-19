package com.ainjob.ats.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplicantUpdateResponse {

    private final Long id;
    private final String status;
    private final LocalDateTime changedAt;

    public ApplicantUpdateResponse(Long applicantId, String status, LocalDateTime changedAt) {
        this.id = applicantId;
        this.status = status;
        this.changedAt = changedAt;
    }
}

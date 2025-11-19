package com.ainjob.ats.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicantUpdateResponse {

    private Long id;
    private String status;
    private LocalDateTime changedAt;
}

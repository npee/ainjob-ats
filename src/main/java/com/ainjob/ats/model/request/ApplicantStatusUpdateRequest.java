package com.ainjob.ats.model.request;

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

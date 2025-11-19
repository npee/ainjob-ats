package com.ainjob.ats.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicantPagedResponse {

    private List<ApplicantResponse> applicants;
    private int page;
    private int size;
    private int totalElements;
}

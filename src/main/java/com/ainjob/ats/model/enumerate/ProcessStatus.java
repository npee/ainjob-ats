package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * ProcessStatus enum
 * APPLIED - 지원
 * DOCUMENT_PASSED - 서류합격
 * INTERVIEW_PENDING - 면접대기
 * ACCEPTED - 합격
 */
@Getter
@AllArgsConstructor
public enum ProcessStatus {

    APPLIED("지원"),
    DOCUMENT_PASSED("서류합격"),
    INTERVIEW_PENDING("면접대기"),
    ACCEPTED("합격");

    private final String description;

    public static ProcessStatus fromDescription(String description) {

        return Arrays.stream(ProcessStatus.values())
                .filter(status -> status.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}

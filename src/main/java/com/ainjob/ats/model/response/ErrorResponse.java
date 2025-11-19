package com.ainjob.ats.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private String detail;

    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }
}

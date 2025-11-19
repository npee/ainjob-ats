package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionType {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    FULLSTACK("풀스택"),
    MOBILE("모바일"),
    DATA_SCIENTIST("데이터 사이언티스트");

    private final String description;
}

package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndustryType {

    IT("정보기술"),
    FINANCE("금융"),
    HEALTHCARE("헬스케어"),
    EDUCATION("교육"),
    MANUFACTURING("제조업"),
    RETAIL("소매업"),
    HOSPITALITY("환대업"),
    TRANSPORTATION("운송업"),
    ENERGY("에너지"),
    ENTERTAINMENT("엔터테인먼트");

    private final String description;
}

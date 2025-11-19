package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MajorGroupCode {

    IT("IT계열"),
    ENG("IT제외 공학"),
    HUM("인문계열"),
    SOC("사회계열"),
    SCI("자연과학계열"),
    ART("예체능계열");

    private final String description;
}

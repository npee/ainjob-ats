package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SkillType {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String description;
}

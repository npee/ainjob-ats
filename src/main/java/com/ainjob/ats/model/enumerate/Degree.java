package com.ainjob.ats.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 'HS (고등학교 졸업)
 * AD (전문학사)
 * BD (학사)
 * MD (석사)
 * DD (박사)
 * ETC-기타'
 */
@Getter
@AllArgsConstructor
public enum Degree {

    HS("고졸"),
    AD("준학사"),
    BD("학사"),
    MD("석사"),
    DD("박사");

    private final String description;

    public static Degree fromDescription(String description) {
        if (description == null || description.isEmpty()) {
            return null;
        }
        if (description.equals("대졸")) {
            return BD;
        }
        return Arrays.stream(Degree.values())
                .filter(degree -> degree.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 값입니다: " + description));
    }

    public static List<Degree> getHigherOrEqual(Degree degree) {
        if (degree == null) {
            return Arrays.asList(Degree.values());
        }
        return Arrays.stream(Degree.values())
                .filter(d -> d.ordinal() >= degree.ordinal())
                .toList();
    }
}

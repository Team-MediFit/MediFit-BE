package com.medifitbe.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WeekendWork {
    가능("가능"),
    가급적_쉬고_싶음("가급적 쉬고 싶음"),
    휴무를_원함("휴무를 원함"),
    NONE("NONE");

    private final String label;

    WeekendWork(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static WeekendWork fromValue(String value) {
        return Arrays.stream(WeekendWork.values())
                .filter(w -> w.label.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown weekend work value: " + value));
    }
}
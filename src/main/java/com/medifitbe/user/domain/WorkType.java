package com.medifitbe.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum WorkType {
    FULL_TIME("풀타임"),
    PART_TIME_MORNING("파트타임 오전 (9-13시)"),
    PART_TIME_AFTERNOON("파트타임 오후 (14-18시)"),
    PART_TIME_EVENING("파트타임 저녁 (18시 이후)"),
    ANYTIME("상관없음"),
    NONE("NONE");

    private final String label;

    WorkType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static WorkType fromValue(String value) {
        return Arrays.stream(WorkType.values())
                .filter(w -> w.label.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown work type: " + value));
    }
}

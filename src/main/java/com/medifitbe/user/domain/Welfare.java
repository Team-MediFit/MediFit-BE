package com.medifitbe.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum Welfare {
    MEAL("식대"),
    TRANSPORT("교통비"),
    BONUS("상여금"),
    PAID_LEAVE("연차보장"),
    LUNCH_BREAK("점심시간보장"),
    ANY("상관없음"),
    NONE("NONE");

    private final String label;

    Welfare(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Welfare fromValue(String value) {
        return Arrays.stream(Welfare.values())
                .filter(w -> w.label.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown welfare: " + value));
    }
}

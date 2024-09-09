package com.example.aiteacher.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Subject {
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    BIOLOGY("Biology"),
    PSYCHOLOGY("Psychology"),
    SOCIOLOGY("Sociology");

    private final String value;

    Subject(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Subject fromString(String value) {
        for (Subject subject : Subject.values()) {
            if (subject.value.equalsIgnoreCase(value)) {
                return subject;
            }
        }
        throw new IllegalArgumentException("Invalid value for subject: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

package com.example.aiteacher.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Programming {
    JAVA("Java"),
    PYTHON("Python"),
    C_SHARP("C#"),
    C_PLUS_PLUS("C++"),
    JAVASCRIPT("JavaScript"),
    RUST("Rust"),
    REACT("React"),
    SWIFT("Swift");

    private final String value;

    Programming(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Programming fromString(String value) {
        for (Programming programming : Programming.values()) {
            if (programming.value.equalsIgnoreCase(value)) {
                return programming;
            }
        }
        throw new IllegalArgumentException("Invalid value for programming language: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

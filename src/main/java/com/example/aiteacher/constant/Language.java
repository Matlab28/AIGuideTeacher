package com.example.aiteacher.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Language {
    ENGLISH("English"),
    GERMAN("German"),
    SPANISH("Spanish"),
    RUSSIAN("Russian"),
    ITALIAN("Italian"),
    FRENCH("French");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Language fromString(String value) {
        for (Language language : Language.values()) {
            if (language.value.equalsIgnoreCase(value)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Invalid value for language: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

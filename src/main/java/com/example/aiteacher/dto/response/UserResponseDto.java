package com.example.aiteacher.dto.response;

import com.example.aiteacher.constant.Language;
import com.example.aiteacher.constant.Programming;
import com.example.aiteacher.constant.Subject;
import com.example.aiteacher.constant.SystemLanguage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private Long id;
    private SystemLanguage systemLanguage;
    private Integer age;
    private String program;
    private Programming programming;
    private Language language;
    private Subject subject;
    private String duration;
    private String purpose;
    private String geminiResponse;
}

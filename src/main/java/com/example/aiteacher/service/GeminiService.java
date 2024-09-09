package com.example.aiteacher.service;

import com.example.aiteacher.dto.gemini.Root;
import com.example.aiteacher.dto.request.UserRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    Root processUserRequest(UserRequestDto dto);

    Root getLatestResponse();
}

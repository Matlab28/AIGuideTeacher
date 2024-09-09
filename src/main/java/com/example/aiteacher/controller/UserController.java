package com.example.aiteacher.controller;

import com.example.aiteacher.dto.gemini.Root;
import com.example.aiteacher.dto.request.UserRequestDto;
import com.example.aiteacher.dto.response.UserResponseDto;
import com.example.aiteacher.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://matlab28.github.io")
public class UserController {
    private final UserService service;

    @PostMapping("/getProgram")
    public ResponseEntity<Root> getProgram(@RequestBody UserRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.processUserRequest(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(null);
        }
    }

    @GetMapping("/latestUpdates")
    public ResponseEntity<Root> getLatestUpdates() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getLatestResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

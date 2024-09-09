package com.example.aiteacher.service;

import com.example.aiteacher.client.GeminiApiClient;
import com.example.aiteacher.constant.Programming;
import com.example.aiteacher.dto.gemini.Candidate;
import com.example.aiteacher.dto.gemini.Root;
import com.example.aiteacher.dto.request.UserRequestDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements GeminiService {
    private final GeminiApiClient client;

    @Value("${gemini.api.key}")
    private String key;
    private Root latestResponse;

    @Override
    public Root processUserRequest(UserRequestDto dto) {

        if (dto.getAge() == null || dto.getAge() <= 0) {
            log.error("Invalid age - " + dto.getAge());
            throw new RuntimeException("Age must be positive number and higher than zero...");
        }

        String instruction = constructInstruction(dto);
        Root updates = getUpdates(instruction);
        String extractedText = extractedTextFromGeminiResponse(updates);
        dto.setGeminiResponse(extractedText);

        log.info("User request has been received.");
        latestResponse = updates;
        return latestResponse;
    }

    @Override
    public Root getLatestResponse() {
        return latestResponse;
    }

    private String constructInstruction(UserRequestDto dto) {
        StringBuilder instruction = new StringBuilder();
        instruction.append("System Language: ").append(dto.getSystemLanguage()).append("\n");
        instruction.append("Age: ").append(dto.getAge()).append("\n");

        switch (dto.getProgram().toLowerCase()) {
            case "programming":
                appendProgrammingInfo(instruction, dto);
                break;
            case "language":
                appendLanguageInfo(instruction, dto);
                break;
            case "subject":
                appendSubjectInfo(instruction, dto);
                break;
            default:
                throw new IllegalArgumentException("Invalid program type: " + dto.getProgram());
        }

        instruction.append("Duration: ").append(dto.getDuration()).append("\n");
        instruction.append("Purpose: ").append(dto.getPurpose()).append("\n");
        instruction.append("Provide the learning program based on the program choice. If user chooses programming, " +
                "teach the chosen programming language, if user chooses language, teach user the language has been " +
                "chosen, if user chooses subject, teach user chosen language. Teach them in the language, which they" +
                " will be choose in \"System Language\". If it \"AZ\", it means Azerbaijan, if it \"EN\", it means " +
                "English, if it \"RU\", it means Russian. Give them chosen program based on duration, age and purpose.");
        return instruction.toString();
    }

    private void appendProgrammingInfo(StringBuilder instruction, UserRequestDto dto) {
        instruction.append("Programming: ").append(dto.getProgramming()).append("\n");
        instruction.append("Programming Valid: ").append(checkProgramming(dto.getProgramming())).append("\n");
    }

    private boolean checkProgramming(Programming programming) {
        return programming != null && EnumSet.allOf(Programming.class).contains(programming);
    }

    private void appendLanguageInfo(StringBuilder instruction, UserRequestDto dto) {
        instruction.append("Language: ").append(dto.getLanguage()).append("\n");
    }

    private void appendSubjectInfo(StringBuilder instruction, UserRequestDto dto) {
        instruction.append("Subject: ").append(dto.getSubject()).append("\n");
    }


    private Root getUpdates(String instruction) {
        try {
            JsonObject json = new JsonObject();
            JsonArray contentsArray = new JsonArray();
            JsonObject contentsObject = new JsonObject();
            JsonArray partsArray = new JsonArray();
            JsonObject partsObject = new JsonObject();

            partsObject.add("text", new JsonPrimitive(instruction));
            partsArray.add(partsObject);
            contentsObject.add("parts", partsArray);
            contentsArray.add(contentsObject);
            json.add("contents", contentsArray);

            String content = json.toString();
            return client.getData(key, content);
        } catch (Exception e) {
            log.error("Error while getting response from Gemini AI: ", e);
            throw e;
        }
    }

    private String extractedTextFromGeminiResponse(Root updates) {
        StringBuilder textBuilder = new StringBuilder();

        if (updates.getCandidates() != null) {
            for (Candidate candidate : updates.getCandidates()) {
                String text = candidate.getContent().getParts().get(0).getText();
                text = text.replace("*", "");
                textBuilder.append(text).append("\n\n");
            }
        }

        String response = textBuilder.toString().trim();

        return response
                .replaceAll("(?i)\\bSystem Language:\\b", "\nSystem Language:\n")
                .replaceAll("(?i)\\bProgramming:\\b", "\nProgramming:\n")
                .replaceAll("(?i)\\bLanguage:\\b", "\nLanguage:\n")
                .replaceAll("(?i)\\bSubject:\\b", "\nSubject:\n")
                .replaceAll("(?i)\\bDuration:\\b", "\nDuration:\n")
                .replaceAll("(?i)\\bPurpose:\\b", "\nPurpose:\n");
    }
}

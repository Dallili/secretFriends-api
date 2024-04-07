package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.service.DiaryService;
import org.dallili.secretfriends.service.MatchingHistoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchingController {

    final MatchingHistoryService matchingHistoryService;

    final DiaryService diaryService;

    @Operation(summary = "Create Known-Matching Diary POST", description = "지인 매칭을 위한 일기장 생성")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> knownMatchingDiaryAdd(DiaryDTO.knownMatchingDiary diaryDTO){

        Long diaryID = diaryService.addKnownMatchingDiary(diaryDTO);

        UUID code = diaryService.findCode(diaryID);

        Map<String, String> result = new HashMap<>();

        result.put("diaryID", diaryID.toString());
        result.put("code", code.toString());

        return result;

    }

/*
    @Operation(summary = "Get Code GET", description = "초대 코드 리턴")
    @GetMapping(value = "/{diaryID}")
    public String codeDetails(Long diaryID){

        String code = diaryService.findOne(diaryID).getCode();

        return code;
    }
    */
}

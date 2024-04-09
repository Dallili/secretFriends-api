package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.service.DiaryService;
import org.dallili.secretfriends.service.MatchingHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchingController {

    final MatchingHistoryService matchingHistoryService;

    final DiaryService diaryService;

/*
    @Operation(summary = "Get Code GET", description = "초대 코드 리턴")
    @GetMapping(value = "/{diaryID}")
    public String codeDetails(Long diaryID){

        String code = diaryService.findOne(diaryID).getCode();

        return code;
    }
    */
}

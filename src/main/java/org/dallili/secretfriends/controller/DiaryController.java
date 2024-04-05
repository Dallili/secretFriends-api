package org.dallili.secretfriends.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.service.DiaryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {


    private final DiaryService diaryService;

    @Operation(summary = "Diary List GET", description = "활성/비활성 일기장 목록 조회")
    @GetMapping(value = "/")
    public Map<String, Object> diaryDTOList (@RequestParam("state") Boolean state, String memberID) {


        List<DiaryDTO> diaries = diaryService.findStateDiaries(memberID, state);

        Map<String, Object> result = new HashMap<>();
        result.put("total", diaries.size());
        result.put("diaries", diaries);


        return result;
    }

    @Operation(summary = "Diary Deactivate PATCH", description = "일기장 비활성화")
    @PatchMapping(value = "/{diaryID}/isActivated")
    public void diaryStateModify (@PathVariable("diaryID") String diaryID){

        diaryService.modifyState(diaryID);

        log.info(diaryService.findOne(diaryID));

    }


    @Operation(summary = "Replied Diary GET", description = "답장 온 일기장 조회")
    @GetMapping(value = "/replied")
    public Map<String, Object> repliedDiaryList(String loginMemberID){

        List<DiaryDTO> diaries =  diaryService.findRepliedDiaries(loginMemberID);
        Map<String, Object> result = new HashMap<>();
        result.put("diaries",diaries);

        return result;

    }

    @Operation(summary = "Delete Diary DELETE", description = "일기장 삭제/지인 매칭 취소")
    @DeleteMapping(value = "/{diaryID}")
    public void diaryRemove(@PathVariable("diaryID") String diaryID) {

        diaryService.removeDiary(diaryID);

    }

}

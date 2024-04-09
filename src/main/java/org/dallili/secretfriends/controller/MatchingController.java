package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.dto.MatchingDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.service.DiaryService;
import org.dallili.secretfriends.service.MatchingHistoryService;
import org.dallili.secretfriends.service.MatchingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchingController {

    final MatchingHistoryService matchingHistoryService;

    final MatchingService matchingService;

    final DiaryService diaryService;


    @Operation(summary = "Create Known-Matching Diary POST", description = "지인 매칭을 위한 일기장 생성")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> knownMatchingDiaryAdd(@Valid @RequestBody DiaryDTO.knownMatchingDiary diaryDTO){

        Long diaryID = diaryService.addKnownsDiary(diaryDTO.getMemberID(), diaryDTO.getColor());

        UUID code = diaryService.findCode(diaryID);

        Map<String, String> result = new HashMap<>();

        result.put("diaryID", diaryID.toString());
        result.put("code", code.toString());

        return result;

    }

    @Operation(summary = "Get Code GET", description = "초대 코드 조회")
    @GetMapping(value = "/{diaryID}")
    public UUID codeDetails(Long diaryID){
        UUID code = diaryService.findCode(diaryID);
        return code;
    }


    @Operation(summary = "Match Knowns PATCH", description = "코드 입력을 통한 다이어리 partner 확정 (=지인 매칭 완료)")
    @PatchMapping(value = "/")
    public Map<String, String> partnerModify(String code, Long userID){

        DiaryDTO diaryDTO = diaryService.findDiaryByCode(code);
        Long diaryID = diaryDTO.getDiaryID();
        Map<String, String> result = new HashMap<>();

        if(diaryDTO.getPartnerID() == null){

            diaryService.modifyUpdate(diaryID, userID);
            diaryService.modifyPartner(diaryID, userID);
            matchingHistoryService.addHistory(diaryDTO.getMemberID(), userID);

            result.put("diaryID", diaryID.toString());
            result.put("state", "매칭 성공");
            return result;
        }
        else {
            result.put("state", "이미 매칭이 완료된 일기장입니다");
            return result;
        }
    }

    @Operation(summary = "Create unKnown-Matching POST", description = "랜덤 매칭 - 일기장 생성 or 매칭 레코드 등록")
    @PostMapping(value = "/unknown", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> unknownMatchingAdd(@Valid @RequestBody MatchingDTO newMatching){

        Map<String, Long> result = matchingService.saveMatchingSearch(newMatching);

        return result;

    }

    @Operation(summary = "Delete unknown-Matching DELETE", description = "랜덤 매칭 취소")
    @DeleteMapping(value = "/{matchingID}")
    public Long matchingRemove(@PathVariable ("matchingID") Long matchingID){

        Long deletedMatchingID = matchingService.removeMatching(matchingID);

        return deletedMatchingID;
    }

}

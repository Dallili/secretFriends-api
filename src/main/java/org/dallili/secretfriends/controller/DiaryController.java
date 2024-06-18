package org.dallili.secretfriends.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.service.EmitterService;
import org.dallili.secretfriends.notify.service.NotifyService;
import org.dallili.secretfriends.service.DiaryService;
import org.dallili.secretfriends.service.MatchingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

    private final EmitterService emitterService;

    private final DiaryService diaryService;

    private final NotifyService notifyService;

    private final MatchingService matchingService;

    @Operation(summary = "Diary List GET", description = "활성/비활성 일기장 목록 조회")
    @GetMapping(value = "/")
    public Map<String, Object> diaryDTOList(@RequestParam("state") Boolean state, Authentication authentication) {

        Long memberID = Long.parseLong(authentication.getName());
        Map<String, Object> result = diaryService.findStateDiaries(memberID, state);

        if(state){ //활성 목록
            List<DiaryDTO.unKnownMatchingDiary> unmatchedUnknownDiaries = matchingService.findUnknownDiary(memberID);
            result.put("unmatchedUnknownDiaries", unmatchedUnknownDiaries);
            int total = (Integer)result.get("total");
            result.put("total", total+ unmatchedUnknownDiaries.size());
        }

        return result;
    }


    @Operation(summary = "Diary Deactivate PATCH", description = "일기장 비활성화")
    @PatchMapping(value = "/{diaryID}/state")
    public void diaryStateModify (@PathVariable("diaryID") Long diaryID){
        diaryService.modifyState(diaryID);
        Long receiverID = diaryService.findDiaryById(diaryID).getMember().getMemberID();
        Long senderID = diaryService.findDiaryById(diaryID).getPartner().getMemberID();
        emitterService.sendEvents(diaryID, receiverID, senderID, NotifyDTO.NotifyType.INACTIVATE);
        emitterService.sendEvents(diaryID, senderID, receiverID, NotifyDTO.NotifyType.INACTIVATE);
        notifyService.saveNotifyTable(diaryID, receiverID, senderID, NotifyDTO.NotifyType.INACTIVATE);

        log.info(diaryService.findOne(diaryID));

    }


    @Operation(summary = "Replied Diary GET", description = "답장 온 일기장 조회")
    @GetMapping(value = "/replied")
    public Map<String, Object> repliedDiaryList(Authentication authentication){

        List<DiaryDTO> diaries =  diaryService.findRepliedDiaries(Long.parseLong(authentication.getName()));
        Map<String, Object> result = new HashMap<>();
        result.put("diaries",diaries);

        return result;

    }

    @Operation(summary = "Delete Diary DELETE", description = "일기장 삭제/지인 매칭 취소")
    @DeleteMapping(value = "/{diaryID}")
    public void diaryRemove(@PathVariable("diaryID") Long diaryID) {

        diaryService.removeDiary(diaryID);

    }

    @Operation(summary = "Diary Take Back PATCH", description = "일기장 회수")
    @PatchMapping(value = "/{diaryID}/takeBack")
    public void diaryTakeBackModify (@PathVariable("diaryID") Long diaryID, Authentication authentication){

        diaryService.modifyDiaryTakeBack(diaryID, Long.parseLong(authentication.getName()));

    }

}

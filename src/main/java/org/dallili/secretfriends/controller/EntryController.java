package org.dallili.secretfriends.controller;

import com.vane.badwordfiltering.BadWordFiltering;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.EntryDTO;
import org.dallili.secretfriends.service.EntryService;
import org.dallili.secretfriends.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entries")
@Log4j2
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    private final MemberService memberService;

    @Operation(summary = "일기 생성", description = "일기 생성 후 임시 저장")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> EntryAdd(@Valid @RequestBody EntryDTO.CreateRequest entryDTO, Authentication authentication, BindingResult bindingResult) throws BindException{

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String,Long> result = new HashMap<>();
        entryDTO.setWriterID(Long.parseLong(authentication.getName()));

        Long entryID = entryService.addEntry(entryDTO);
        result.put("entryID",entryID);

        return result;

    }

    @Operation(summary = "일기 수정", description = "전달되지 않은 일기의 content 필드 값 업데이트")
    @PutMapping(value = "/{entryID}")
    public EntryDTO.UnsentEntryResponse contentModify(@Valid @RequestBody EntryDTO.ModifyRequest request, BindingResult bindingResult) throws BindException{

        log.info(request);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        EntryDTO.UnsentEntryResponse response = entryService.modifyContent(request);
        log.info(response);

        return response;
    }

    @Operation(summary = "일기 전달", description = "저장된 일기의 state, sendAt 필드 값 업데이트")
    @PatchMapping(value = "/{entryID}")
    public Map<String,String> stateModify(@PathVariable("entryID") Long entryID, Authentication authentication){
        Long memberID = Long.parseLong(authentication.getName());
        Boolean result = entryService.modifyState(entryID,memberID);
        if(result){
            return Map.of("entryID",Long.toString(entryID),
                    "result","일기 전달 성공");
        } else 
            return Map.of("entryID",Long.toString(entryID),
                    "result","이미 전달된 일기");

    }

    @Operation(summary = "일기 조회", description = "특정 다이어리의 일기 목록 조회")
    @GetMapping(value = "/list/{diaryID}")
    public Map<String,Object> entryList(@PathVariable("diaryID") Long diaryID, Authentication authentication){
        Long memberID = Long.parseLong(authentication.getName());
        Boolean useFiltering = memberService.findMemberUseFiltering(memberID);

        List<EntryDTO.SentEntryResponse> SentEntry = entryService.findSentEntry(diaryID);
        List<EntryDTO.UnsentEntryResponse> UnsentEntry = entryService.findUnsentEntry(diaryID);
        Map<String,Object> result = new HashMap<>();

        if (useFiltering == true){
            SentEntry = entryService.modifyTextFiltering(SentEntry);
        }

        result.put("total",SentEntry.size());
        result.put("sent",SentEntry);
        result.put("unsent",UnsentEntry);



        return result;
    }
}

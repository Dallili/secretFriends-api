package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.EntryDTO;
import org.dallili.secretfriends.service.EntryService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/entries")
@Log4j2
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @Operation(summary = "Entries POST", description = "일기 생성")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> EntryAdd(@Valid @RequestBody EntryDTO entryDTO, BindingResult bindingResult) throws BindException{

        log.info(entryDTO);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String,Long> result = new HashMap<>();

        Long entryID = entryService.addEntry(entryDTO);
        result.put("entryID",entryID);

        return result;

    }

    @Operation(summary = "일기 전달", description = "저장된 일기의 state, sendAt 필드 값 업데이트")
    @PatchMapping(value = "/{entryID}")
    public Map<String,String> stateModify(@PathVariable("entryID") Long entryID){
        Boolean result = entryService.modifyState(entryID);
        if(result){
            return Map.of("entryID",Long.toString(entryID),
                    "result","일기 전달 성공");
        } else 
            return Map.of("entryID",Long.toString(entryID),
                    "result","이미 전달된 일기");

    }
}
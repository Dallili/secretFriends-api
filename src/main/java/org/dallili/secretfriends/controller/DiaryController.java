package org.dallili.secretfriends.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {


    private final DiaryService diaryService;

    @Operation(summary = "Diary List GET", description = "활성/비활성 일기장 목록 조회")
    @GetMapping(value = "/{state}")
    public List<DiaryDTO> diaryDTOList (@PathVariable("state") Boolean state, String userID) {
        List<DiaryDTO> diaries = diaryService.findAllDiaries()
                .stream()
                .filter(diaryDTO -> diaryDTO.isActivated() == state)
                .filter(diaryDTO -> userID.equals(diaryDTO.getUserID()) || userID.equals(diaryDTO.getPartnerID()))
                .collect(Collectors.toList());
        log.info(state+ "상태인 일기장 목록: " + diaries);
        return diaries;
    }




}

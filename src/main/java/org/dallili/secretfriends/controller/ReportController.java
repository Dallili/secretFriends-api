package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.dto.GptDTO;
import org.dallili.secretfriends.dto.ReportDTO;
import org.dallili.secretfriends.service.EntryService;
import org.dallili.secretfriends.service.ReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "일기 감정 분석 레포트 조회", description = "해당 entryID에 대한 감정분석 결과 조회")
    @GetMapping("/{entryID}/reports")
    @ResponseStatus(HttpStatus.OK)
    public ReportDTO.Details reportDetails(@PathVariable("entryID") Long entryID){
        return reportService.findReport(entryID);
    }

    @Operation(summary = "사용자의 최근 5개 일기 감정 분석 레포트 조회", description = "사용자 ID와 사용자의 다이어리 ID를 받아 최근 5개 일기 감정분석 결과 조회")
    @GetMapping("/{diaryID}/reports/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDTO.List> reportList(Authentication authentication, @PathVariable("diaryID") Long diaryID){
        Long memberID = Long.parseLong(authentication.getName());
        return reportService.findReportList(memberID,diaryID);
    }


}

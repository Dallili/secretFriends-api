package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.NoticeDTO;
import org.dallili.secretfriends.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Log4j2
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 생성 (테스트용)", description = "공지사항 조회를 위한 데이터 생성 목적으로 만들어짐")
    @PostMapping
    public Long noticeAdd(@RequestBody @Valid NoticeDTO.CreateRequest request){
        return noticeService.addNotice(request);
    }

    @GetMapping
    public List<NoticeDTO.ListResponse> noticeList(){
        return noticeService.findNoticeList();
    }

    @GetMapping("/{noticeID}")
    public NoticeDTO.DetailsResponse noticeDetails(@PathVariable Long noticeID){
        return noticeService.findNoticeDetails(noticeID);
    }
}

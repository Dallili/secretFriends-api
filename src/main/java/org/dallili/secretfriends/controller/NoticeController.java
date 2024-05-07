package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.NoticeDTO;
import org.dallili.secretfriends.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Operation(summary = "공지사항 목록 조회")
    @GetMapping
    public Map<String,Object> noticeList(){
        List<NoticeDTO.ListResponse> pinList = noticeService.findNoticeList(true);
        List<NoticeDTO.ListResponse> unpinList = noticeService.findNoticeList(false);

        Map<String,Object> result = new HashMap<>();
        result.put("pinList",pinList);
        result.put("unpinList",unpinList);

        return result;
    }

    @Operation(summary = "공지사항 상세 조회")
    @GetMapping("/{noticeID}")
    public NoticeDTO.DetailsResponse noticeDetails(@PathVariable Long noticeID){
        return noticeService.findNoticeDetails(noticeID);
    }
}

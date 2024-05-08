package org.dallili.secretfriends.notify.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.service.EmitterService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/emitter")
public class EmitterController {

    private final EmitterService emitterService;
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter emitterAdd(Authentication authentication) { // 오로지 emitter를 등록하는 역할만 하는 메소드
        Long memberID = Long.parseLong(authentication.getName());
        SseEmitter emitter = new SseEmitter(Long.valueOf(3 * 3600 * 1000));
        return emitterService.addEmitter(memberID, emitter);
        // 따로 sendEvents()를 여기 선언할 필요 없음.
        // 이유: 알림이 발생해야 하는 경우들의 (ex diaryService.modifyState, diaryService.modifyPartner)
        // 서비스의 메소드 끝에 EmitterService.sendEvents()를 붙여주면 됨.
        // 그러면 emitter 연결이 끊기기 전까지 계속 알림 발생 시킬 수 있음.

    }

}
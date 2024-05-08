package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.EmitterRepository;
import org.dallili.secretfriends.notify.service.EmitterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootTest
@Log4j2
public class EmitterServiceTests {

    @Autowired
    private EmitterService emitterService;

    @Autowired
    private EmitterRepository emitterRepository;

    @Test
    public void testSendEvents(){

        Long receiverID = 1L;
        Long senderID = 2L;
        SseEmitter emitter1 = new SseEmitter();
        SseEmitter emitter2 = new SseEmitter();

        emitterService.addEmitter(receiverID, emitter1);
        emitterService.addEmitter(senderID, emitter2);
        emitterRepository.findAllID();

        emitterService.sendEvents(1L,1L, 2L, NotifyDTO.NotifyType.NEWDIARY);
        emitterService.sendEvents(1L,1L, 2L, NotifyDTO.NotifyType.REPLY);
        emitterService.sendEvents(1L,1L, 2L, NotifyDTO.NotifyType.INACTIVATE);
    }



}

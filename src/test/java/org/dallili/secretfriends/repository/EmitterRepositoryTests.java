package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.repository.EmitterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Log4j2
public class EmitterRepositoryTests {

    @Autowired
    private EmitterRepository emitterRepository;

    @Test
    public void testSaveAndDelete() {
        Long receiverID = 1L;
        Long senderID = 2L;
        SseEmitter emitter1 = new SseEmitter();
        SseEmitter emitter2 = new SseEmitter();

        emitterRepository.save(receiverID, emitter1);
        emitterRepository.save(senderID, emitter2);
        emitterRepository.findAllID();
        // 저장된 emitter가 있는지 확인.
        assertNotNull(emitterRepository.findById(receiverID));

        //emitterRepository.deleteById(receiverID);

        // 삭제 후 emitter가 없는지 확인. null이 아니라면 테스트 실패
        //assertNull(emitterRepository.findById(receiverID));
    }

    @Test
    public void testPrintEmitters() {

        emitterRepository.findAllID();

    }



}

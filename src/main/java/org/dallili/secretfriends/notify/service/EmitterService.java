package org.dallili.secretfriends.notify.service;

import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterService {

    void sendDummyEvents(Long receiverID, Object data);

    // 특정 이벤트 발생 시, 1. emitter를 통해 유저에게 알림 보내고 2. notify 테이블에 알림 데이터 저장하는 메소드
    void sendEvents(Long receiverID, Long senderID, NotifyDTO.NotifyType type);

    // "1. emitter를 통해 유저에게 알림 보내고" 담당하는 메소드
    Map<String, Object> addNewEvents(String senderNickname, NotifyDTO.NotifyType type);

    SseEmitter addEmitter(Long memberID, SseEmitter emitter);
}

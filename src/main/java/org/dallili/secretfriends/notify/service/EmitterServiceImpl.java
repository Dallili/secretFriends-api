package org.dallili.secretfriends.notify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.EmitterRepository;
import org.dallili.secretfriends.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmitterServiceImpl implements EmitterService{

    private final EmitterRepository emitterRepository;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final NotifyService notifyService;


    public Map<String, Object> addNewEvents(String senderNickname, NotifyDTO.NotifyType type){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Map<String, Object> EventData = new HashMap<>();
        switch (type){
            case NEWDIARY: EventData.put("message", "님과의 일기장이 만들어졌어요."); break;
            case REPLY : EventData.put("message", "님으로 부터 답장이 왔어요."); break;
            case INACTIVATE : EventData.put("message", "님과의 일기장이 비활성화 되었어요."); break;
        }
        EventData.put("type", type);
        EventData.put("timestamp", formattedDateTime);
        EventData.put("opponent", senderNickname); // 상대방

        //log.info(EventData);

        return EventData;
    }

    public void sendDummyEvents(Long receiverID, Object data){
        SseEmitter emitter = emitterRepository.findById(receiverID);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(receiverID)).name("SSE Initialize").data(data));
                emitter.send("success");
            } catch (IOException exception) {
                emitterRepository.deleteById(receiverID);
                emitter.completeWithError(exception);
            }
        }
    }

    public void sendEvents(Long receiverID, Long senderID, NotifyDTO.NotifyType type){

        SseEmitter receiverEmitter = emitterRepository.findById(receiverID);
        String senderNickname = memberService.findMemberById(senderID).getNickname();

        switch (type){
            case NEWDIARY : // 알림 수신 대상: 양쪽 다
                SseEmitter senderEmitter = emitterRepository.findById(senderID);
                String receiverNickname = memberService.findMemberById(receiverID).getNickname();
                try {
                    // receiver에게 보낼 알림
                    // 1. 데이터 생성 및 JSON으로 변환
                    String receiverJson = objectMapper.writeValueAsString(addNewEvents(senderNickname, type));
                    log.info(receiverJson);
                    // 3. receiver의 emitter로 전송
                    receiverEmitter.send(receiverJson, MediaType.APPLICATION_JSON);
                    // 4. notify 테이블에 저장
                    notifyService.saveNotifyTable(receiverID, senderID, type);

                    // sender에게 보낼 알림
                    // 1. 데이터 생성 및 JSON으로 변환
                    String senderJson = objectMapper.writeValueAsString(addNewEvents(receiverNickname, type)); //json으로 변환
                    log.info(senderJson);
                    // 3. receiver의 emitter로 전송
                    senderEmitter.send(senderJson, MediaType.APPLICATION_JSON); //JSON 데이터를 emitter로 전달
                    // 4. notify 테이블에 저장
                    notifyService.saveNotifyTable(senderID,receiverID, type);

                } catch (IOException e) {
                    receiverEmitter.complete();
                    emitterRepository.deleteById(receiverID);
                    senderEmitter.complete();
                    emitterRepository.deleteById(senderID);
                }
                break;

            case REPLY, INACTIVATE: // 알림 수신 대상: 한 쪽만
                try {
                    // receiver에게 보낼 알림
                    // 1. 데이터 생성 및 JSON으로 변환
                    String receiverJson = objectMapper.writeValueAsString(addNewEvents(senderNickname, type));
                    log.info(receiverJson);
                    // 3. receiver의 emitter로 전송
                    receiverEmitter.send(receiverJson, MediaType.APPLICATION_JSON);
                    // 4. notify 테이블에 저장
                    notifyService.saveNotifyTable(receiverID, senderID, type);

                } catch (IOException e) {
                    receiverEmitter.complete();
                    emitterRepository.deleteById(receiverID);
                }
                break;

        }
    }

    public SseEmitter addEmitter(Long memberID, SseEmitter emitter){
        SseEmitter createdEmitter = emitterRepository.save(memberID, emitter);
        sendDummyEvents(memberID,"EventStream Created. [memberID=" + memberID + "]");
        return createdEmitter;

    }
}

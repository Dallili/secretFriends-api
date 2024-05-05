package org.dallili.secretfriends.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter findById(Long memberID);
    SseEmitter save(Long memberID, SseEmitter sseEmitter);
    void deleteById(Long memberID);
    void findAllID();

}
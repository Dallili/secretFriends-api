package org.dallili.secretfriends.notify.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

    @Repository
    @Log4j2
    public class EmitterRepositoryImpl implements EmitterRepository{
        private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

        @Override
        public SseEmitter findById(Long memberID){
            return emitters.get(memberID);
        }
        @Override
        public void findAllID(){
            for(Map.Entry<Long, SseEmitter> emitterOfOne : emitters.entrySet()){
                log.info(emitterOfOne.getKey()+ "\n\n");
            }
        }
        @Override
        public SseEmitter save(Long memberID, SseEmitter emitter) {
            emitters.put(memberID, emitter);
            emitter.onCompletion(()-> emitters.remove(memberID, emitter));
            emitter.onTimeout(()-> emitters.remove(memberID, emitter));

            /* 디버그 용
            for(Map.Entry<Long, SseEmitter> emitterOfOne : emitters.entrySet()) {
                log.info(emitterOfOne.getKey()+ "\n\n");
            }*/

            return emitter;
        }


        @Override
        public void deleteById(Long memberID) {
            emitters.remove(memberID);
        }



    }


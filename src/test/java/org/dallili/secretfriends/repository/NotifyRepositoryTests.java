package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.NotifyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class NotifyRepositoryTests {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private MemberRepository memberRepository;
/*
    @Test
    public void testInsert() {

        Member receiver = memberRepository.findById(1L).orElseThrow();
        Member sender = memberRepository.findById(2L).orElseThrow();

        Notify notify = Notify.builder()
                .notifyType("NEWDIARY")
                .receiver(receiver)
                .sender(sender)
                .build();

        notifyRepository.save(notify);
    }*/
}

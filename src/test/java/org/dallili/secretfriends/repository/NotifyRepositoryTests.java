package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.NotifyRepository;
import org.dallili.secretfriends.service.DiaryService;
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

    @Autowired
    private DiaryService diaryService;


    @Test
    public void testInsert() {

        Member receiver = memberRepository.findById(1L).orElseThrow();
        Member sender = memberRepository.findById(2L).orElseThrow();
        Diary diary = diaryService.findDiaryById(1L);

        Notify notify = Notify.builder()
                .notifyType(NotifyDTO.NotifyType.INACTIVATE)
                .receiver(receiver)
                .sender(sender)
                .diary(diary)
                .build();

        notifyRepository.save(notify);
    }
}

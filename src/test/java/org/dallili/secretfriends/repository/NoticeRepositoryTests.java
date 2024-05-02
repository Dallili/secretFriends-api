package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class NoticeRepositoryTests {
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    public void testInsertNotice(){
        Notice notice = Notice.builder()
                .title("테스트 공지 제목")
                .content("테스트 공지 내용")
                .pin(true)
                .build();
        noticeRepository.save(notice);
    }
}

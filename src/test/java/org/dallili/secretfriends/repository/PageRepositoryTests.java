package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PageRepositoryTests {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    @Test
    public void testInsertPage(){
        Diary diary = diaryRepository.findById("diary1").orElseThrow();

        IntStream.rangeClosed(1,10).forEach(i->{
            Page page = Page.builder()
                    .diary(diary)
                    .writer(diary.getUser().getUserID())
                    .date(LocalDateTime.now())
                    .text("일기 텍스트...")
                    .sendAt(null)
                    .state("N")
                    .build();

            pageRepository.save(page);
        });
    }
}

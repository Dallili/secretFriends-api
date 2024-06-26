package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class EntryRepositoryTests {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void testInsertEntry(){
        Diary diary = diaryRepository.findById(1L).orElseThrow();
        Member member = memberRepository.findById(diary.getMember().getMemberID()).orElseThrow();

        IntStream.rangeClosed(1,10).forEach(i->{
            Entry entry = Entry.builder()
                    .diary(diary)
                    .member(member)
                    .content("일기 텍스트...")
                    .build();

            entryRepository.save(entry);
        });
    }

    @Test
    public void testSelectEntry(){
        Long diaryID = 2L;

        List<Entry> entries =  entryRepository.selectEntry(diaryID,"Y");

        log.info(entries);

    }
}

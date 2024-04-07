package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsertDiary(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Optional<Member> member = memberRepository.findById(1L);
            Optional<Member> partner = memberRepository.findById(2L);

            Diary diary = Diary.builder()
                    .member(member.orElseThrow())
                    .partner(partner.orElseThrow())
                    .state(i%2==0?true:false)
                    .updatedBy(member.orElseThrow().getMemberID())
                    .updatedAt(LocalDateTime.now())
                    .color("#000000")
                    .build();

            diaryRepository.save(diary);

        });
    }

    @Test
    public void testInsertKnownMatchingDiary(){
        IntStream.rangeClosed(100,150).forEach(i->{

            Optional<Member> member = memberRepository.findById(i-50L);

            UUID code = UUID.randomUUID();

            Diary diary = Diary.builder()
                    .member(member.orElseThrow())
                    .code(code)
                    .color("#000000")
                    .build();

            diaryRepository.save(diary);

        });
    }

}

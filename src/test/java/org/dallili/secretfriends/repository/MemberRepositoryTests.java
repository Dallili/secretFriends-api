package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsertMember(){
        IntStream.rangeClosed(1,10).forEach(i->{
            Member member = Member.builder()
                    .password("1234")
                    .nickname("member"+i)
                    .birthday(LocalDate.of(2023,i%12+1,i%28+1))
                    .email("main"+i+"@gmail.com")
                    .gender(i%2==0?"F":"M")
                    .useFiltering(false)
                    .build();
            memberRepository.save(member);
        });
    }
}

package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;


    @Test
    void singUp() {
        MemberDTO.SignUpRequest requestDto = MemberDTO.SignUpRequest.builder()
                .email("test@test.com")
                .password("1234")
                .gender("F")
                .nickname("test")
                .birthday(LocalDate.of(2002,8,22))
                .build();

        memberService.singUp(requestDto);
    }

    @Test
    void findMember(){
        Long memberID = 1L;
        MemberDTO.DetailsResponse response = memberService.findMember(memberID);
        log.info(response);
    }
}

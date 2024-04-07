package org.dallili.secretfriends.service;

import org.dallili.secretfriends.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
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
}

package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.User;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@SpringBootTest
@Log4j2
public class DiaryServiceTests {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegister() {

        Optional<User> user = userRepository.findById("user1");
        Optional<User> partner = userRepository.findById("user10");

               DiaryDTO diaryDTO = DiaryDTO.builder()
                .diaryID("diary101")
                .color("#123456")
                .isActivated(true)
                .user(user.orElseThrow())
                .partner(partner.orElseThrow())
                .updatedBy("user10")
                .updatedAt(LocalDateTime.now())
                        .build();

        log.info(diaryService.register(diaryDTO));
    }

    @Test
    public void testReadOne() {

        DiaryDTO diaryDTO = diaryService.readOne("diary4");

        log.info(diaryDTO);
    }
}

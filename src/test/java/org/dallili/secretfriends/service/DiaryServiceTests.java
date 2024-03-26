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

@SpringBootTest
@Log4j2
public class DiaryServiceTests {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegister() {

        //Optional<User> user = userRepository.findById("user1");
        //Optional<User> partner = userRepository.findById("user10");

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .diaryID("diary777")
                .color("#123456")
                .isActivated(true)
                .userID("user1")
                .partnerID("user10")
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

    @Test
    public void testUpdate() {

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .diaryID("diary10")
                .updatedAt(LocalDateTime.now())
                .updatedBy("new user")
                .build();

        log.info("업데이트 할 것 : "+ diaryDTO);

        diaryService.update(diaryDTO);
    }

    @Test
    public void testRemove() {

        diaryService.remove("diary1");

    }

    @Test
    public void testModifyPartner() {

        diaryService.modifyPartner("diary103", "user77");

    }

    @Test
    public void testModifyState() {

        diaryService.modifyState("diary10");
    }
}

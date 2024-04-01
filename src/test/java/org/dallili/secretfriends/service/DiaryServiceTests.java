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
import java.util.List;
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
                .diaryID("diary107")
                .color("#123456")
                .state(true)
                .userID("user1")
                .partnerID("user10")
                .updatedBy("user10")
                .updatedAt(LocalDateTime.now())
                .build();


        log.info(diaryService.addDiary(diaryDTO));
    }


    @Test
    public void testReadOne() {

        DiaryDTO diaryDTO = diaryService.findOne("diary4");

        log.info(diaryDTO);
    }

    @Test
    public void testUpdate() {

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .diaryID("diary10")
                .updatedBy("new user")
                .build();

        log.info("업데이트 할 것 : "+ diaryDTO);

        diaryService.modifyUpdate(diaryDTO);
    }

    @Test
    public void testRemove() {

        diaryService.removeDiary("diary1");

    }

    @Test
    public void testModifyPartner() {

        diaryService.modifyPartner("diary1", "user7");

    }

    @Test
    public void testModifyState() {

        diaryService.modifyState("diary10");
    }

    @Test
    public void testFindAllDiaries() {

        log.info(diaryService.findAllDiaries());

    }

    @Test
    public void testFindStateDiaries(){

        List<DiaryDTO> diaries = diaryService.findStateDiaries("user100", true);

        log.info(diaries);
    }

    @Test
    public void testFindRepliedDiaries() {

        List<DiaryDTO> diaries = diaryService.findRepliedDiaries("user10");

        log.info(diaries);
    }

}

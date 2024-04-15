package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class DiaryServiceTests {

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testRegister() {


        DiaryDTO diaryDTO = DiaryDTO.builder()
                .color("#123456")
                .state(true)
                .memberID(1L)
                .partnerID(2L)
                .updatedBy(1L)
                .updatedAt(LocalDateTime.now())
                .build();


        log.info(diaryService.addDiary(diaryDTO));
    }


    @Test
    public void testReadOne() {

        DiaryDTO diaryDTO = diaryService.findOne(4L);

        log.info(diaryDTO);
    }

    @Test
    public void testUpdate() {

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .diaryID(10L)
                .updatedBy(1L)
                .build();

        log.info("업데이트 할 것 : "+ diaryDTO);

        diaryService.modifyUpdate(diaryDTO.getDiaryID(), diaryDTO.getMemberID());
    }

    @Test
    public void testRemove() {

        diaryService.removeDiary(1L);

    }

    @Test
    public void testModifyPartner() {

        diaryService.modifyPartner("fc2413e4-559a-45c2-b94c-a0d45da9c2c9", 1L);

    }

    @Test
    public void testModifyState() {

        diaryService.modifyState(10L);
    }

    @Test
    public void testFindAllDiaries() {

        log.info(diaryService.findAllDiaries());

    }

    @Test
    public void testFindStateDiaries(){

        List<DiaryDTO> diaries = diaryService.findStateDiaries(1L, true);

        log.info(diaries);
    }

    @Test
    public void testFindRepliedDiaries() {

        List<DiaryDTO> diaries = diaryService.findRepliedDiaries(100L);

        log.info(diaries);
    }

    @Test
    public void testFindCode() {

        log.info(diaryService.findCode(112L));

    }

    @Test
    public void testFindByCode() {

        log.info(diaryService.findDiaryByCode("fc2413e4-559a-45c2-b94c-a0d45da9c2c9"));
    }

    @Test
    public void testAddKnownMatchingDiary() {

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .memberID(12L)
                .color("123123")
                .build();

        log.info(diaryDTO);

        log.info(diaryService.addKnownMatchingDiary(diaryDTO));
    }

}

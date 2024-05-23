package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Matching;
import org.dallili.secretfriends.dto.MatchingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class MatchingServiceTests {

    @Autowired
    MatchingHistoryService matchingHistoryService;

    @Autowired
    MatchingService matchingService;

    @Test
    public void testInsertMatchingHistory() {

        log.info(matchingHistoryService.addHistory(1L, 2L));

    }

    @Test
    public void testAddMatching(){

        MatchingDTO matchingDTO = MatchingDTO.builder()
                .createdAt(LocalDateTime.now())
                .memberID(15L)
                .firstInterest(3L)
                .secondInterest(5L)
                .thirdInterest(6L)
                .build();

        log.info(matchingService.addMatching(matchingDTO));
    }

    @Test
    public void testRemoveMatching(){

        matchingService.removeMatching(11L);
    }

    @Test
    public void testSaveMatchingSearch() {

        MatchingDTO.newMatching matchingDTO = MatchingDTO.newMatching.builder()
                .createdAt(LocalDateTime.now())
                .firstInterest(9L)
                .secondInterest(4L)
                .thirdInterest(8L)
                .build();


        matchingService.saveMatchingSearch(matchingDTO, 9L);


    }

    @Test
    public void testModifyMatchingToDiary() {

        log.info(matchingService.findUnknownDiary(101L));
    }

    @Test
    public void testFindHistory(){

        log.info(matchingHistoryService.findHistory(102L, 1L));
    }
}

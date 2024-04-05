package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MatchingServiceTests {

    @Autowired
    MatchingHistoryService matchingHistoryService;

    @Test
    public void testInsertMatchingHistory() {

        log.info(matchingHistoryService.addHistory("user10", "user20"));

    }
}

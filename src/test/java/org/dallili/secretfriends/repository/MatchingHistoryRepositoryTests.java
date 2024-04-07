package org.dallili.secretfriends.repository;


import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.MatchingHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MatchingHistoryRepositoryTests {

    @Autowired
    MatchingHistoryRepository matchingHistoryRepository;

    @Test
    public void testInsertMatchingHistory() {

        Random random = new Random();

        IntStream.rangeClosed(1,30).forEach(i->{
            MatchingHistory matchingHistory = MatchingHistory.builder()
                    .memberID("member"+random.nextInt(100))
                    .partnerID("member"+random.nextInt(100))
                    .build();
            matchingHistoryRepository.save(matchingHistory);
        });

    }
}

package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Matching;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MatchingRepositoryTests {

    @Autowired
    MatchingRepository matchingRepository;

    @Test
    public void testInsertMatching() {

        Random random = new Random();

        IntStream.rangeClosed(1,10).forEach(i-> {

            LocalDateTime customTime = LocalDateTime.of(2024, 4, 8, i, 30, 0);

            Matching matching = Matching.builder()
                    .memberID(Long.valueOf(i))
                    .createdAt(customTime)
                    .firstInterest(Long.valueOf(random.nextInt(9)+1))
                    .secondInterest(Long.valueOf(random.nextInt(9)+1))
                    .thirdInterest(Long.valueOf(random.nextInt(9)+1))
                    .build();
            matchingRepository.save(matching);
        });

    }

}

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

        IntStream.rangeClosed(1,50).forEach(i-> {
            Matching matching = Matching.builder()
                    .memberID("member"+i)
                    .createdAt(LocalDateTime.now())
                    .firstInterest(Long.valueOf(random.nextInt(10)))
                    .secondInterest(Long.valueOf(random.nextInt(10)))
                    .thirdInterest(Long.valueOf(random.nextInt(10)))
                    .build();
            matchingRepository.save(matching);
        });

    }

}

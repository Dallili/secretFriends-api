package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Interest;
import org.dallili.secretfriends.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class InterestRepositoryTests {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void testInsertUser(){
        IntStream.rangeClosed(1,10).forEach(i->{
            Interest interest = Interest.builder()
                    .type("취미"+i)
                    .build();
            interestRepository.save(interest);
        });
    }
}

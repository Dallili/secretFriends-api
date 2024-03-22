package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsertUser(){
        IntStream.rangeClosed(1,100).forEach(i->{
            User user = User.builder()
                    .userID("user"+i)
                    .password("1234")
                    .nickname("user"+i)
                    .birthday(LocalDate.of(2023,i%12+1,i%28+1))
                    .email("main"+i+"@gmail.com")
                    .gender(i%2==0?'F':'M')
                    .useFiltering(false)
                    .build();
            userRepository.save(user);
        });
    }
}

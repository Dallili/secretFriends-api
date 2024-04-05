package org.dallili.secretfriends.repository;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsertDiary(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Optional<User> user = userRepository.findById("user"+i);
            Optional<User> partner = userRepository.findById("user"+((i+1)%100+1));

            Diary diary = Diary.builder()
                    .diaryID("diary"+i)
                    .user(user.orElseThrow())
                    .partner(partner.orElseThrow())
                    .state(i%2==0?true:false)
                    .updatedBy(user.orElseThrow().getUserID())
                    .updatedAt(LocalDateTime.now())
                    .color("#000000")
                    .build();

            diaryRepository.save(diary);

        });
    }

    @Test
    public void testInsertBlankDiary(){
        IntStream.rangeClosed(100,110).forEach(i->{

            Optional<User> user = userRepository.findById("user1");
            Optional<User> partner = userRepository.findById("user2");

            Diary diary = Diary.builder()
                    .diaryID("diary"+i)
                    .user(user.orElseThrow())
                    .partner(partner.orElseThrow())
                    .state(i%2==0?true:false)
                    .color("#000000")
                    .build();

            diaryRepository.save(diary);

        });
    }

}

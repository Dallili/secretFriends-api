package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.code = :code")
    Optional<Diary> selectDiary(@Param("code") UUID code);

    Optional<Diary> findByCode(UUID code);

}

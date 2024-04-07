package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}

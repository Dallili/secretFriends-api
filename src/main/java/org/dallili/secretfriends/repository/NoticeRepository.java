package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    List<Notice> findAllByPin(boolean pin);
}

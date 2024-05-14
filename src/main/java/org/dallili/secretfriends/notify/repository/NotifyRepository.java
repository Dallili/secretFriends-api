package org.dallili.secretfriends.notify.repository;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.notify.domain.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotifyRepository extends JpaRepository<Notify, Long> {

      List<Notify> findAllByReceiver_MemberID(Long receiverID);

}
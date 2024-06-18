package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.MatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {

    List<MatchingHistory> findAllByMemberID(String memberID);
}

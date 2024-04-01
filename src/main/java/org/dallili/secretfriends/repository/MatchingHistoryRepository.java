package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.MatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {
}

package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
}

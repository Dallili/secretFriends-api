package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    List<Matching> findAllByMemberID(Long memberID);

}

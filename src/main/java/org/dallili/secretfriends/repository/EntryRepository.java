package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EntryRepository extends JpaRepository<Entry,Long> {
    @Modifying
    @Query("update Entry e set e.state = 'Y' where e.entryID = :entryID")
    int updateState(@Param("entryID") Long eid);

    @Modifying
    @Query("update Entry e set e.sendAt = :sendAt  where e.entryID = :entryID")
    int updateSendAt(@Param("entryID") Long eid, @Param("sendAt") LocalDateTime sendAt);

}

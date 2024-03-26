package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PageRepository extends JpaRepository<Page,Long> {
    @Modifying
    @Query("update Page p set p.state = 'Y' where p.pageID = :pageID")
    int updateState(@Param("pageID") Long pid);

    @Modifying
    @Query("update Page p set p.sendAt = :sendAt  where p.pageID = :pageID")
    int updateSendAt(@Param("pageID") Long pid, @Param("sendAt") LocalDateTime sendAt);

}

package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByEntry_EntryID(Long entryID);

    @Query("select r from Report r where r.entry.diary.diaryID = :diaryID and r.entry.member.memberID = :memberID order by r.createdAt desc limit 5")
    List<Report> findReportsByMemberAndDiary(Long memberID, Long diaryID);
}

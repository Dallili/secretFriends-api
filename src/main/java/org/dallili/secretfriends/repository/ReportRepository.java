package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByEntry_EntryID(Long entryID);
}

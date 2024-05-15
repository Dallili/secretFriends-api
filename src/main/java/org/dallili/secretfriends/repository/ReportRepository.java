package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}

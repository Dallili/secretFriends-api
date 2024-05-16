package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.dto.ReportDTO;

import java.util.List;

public interface ReportService {

    void addReport(Entry entry);
    ReportDTO.Details findReport(Long entryID);

    List<ReportDTO.List> findReportList(Long memberID, Long diaryID);

}

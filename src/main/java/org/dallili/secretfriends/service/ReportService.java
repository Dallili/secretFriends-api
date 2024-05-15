package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.dto.ReportDTO;

public interface ReportService {

    ReportDTO.Details addReport(Entry entry);

}

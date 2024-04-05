package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.domain.MatchingHistory;

@Transactional
public interface MatchingHistoryService {

    Long addHistory(String memberID, String partnerID);

}

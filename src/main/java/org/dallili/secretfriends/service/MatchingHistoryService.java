package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.domain.MatchingHistory;

@Transactional
public interface MatchingHistoryService {

    Long addHistory(Long memberID, Long partnerID);

    Boolean findHistory(Long memberID, Long partnerID);

}

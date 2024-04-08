package org.dallili.secretfriends.service;

import org.dallili.secretfriends.dto.MatchingDTO;

public interface MatchingService {

    Long addMatching(MatchingDTO matchingDTO);

    void removeMatching(Long matchingID);


}

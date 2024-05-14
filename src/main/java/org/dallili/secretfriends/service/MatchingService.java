package org.dallili.secretfriends.service;

import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.dto.MatchingDTO;

import java.util.List;
import java.util.Map;

public interface MatchingService {

    List<DiaryDTO> findUnknownDiary(Long memberID);

    Long addMatching(MatchingDTO matchingDTO);

    Long removeMatching(Long matchingID);

    Map<String, Object> saveMatchingSearch(MatchingDTO.newMatching matching, Long newUserID);
    // 매칭이 성공할 경우 Matching 테이블에서 old user의 레코드를 삭제 후, new user와 함께 diary 테이블에 등록
    // 매칭이 실패할 경우 new user를 Matching 테이블에 등록


}

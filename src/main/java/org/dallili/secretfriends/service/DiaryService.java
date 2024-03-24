package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.dto.DiaryDTO;

public interface DiaryService {

    String register(DiaryDTO diaryDTO); // 일기장 등록

    DiaryDTO readOne(String diaryID); // 일기장 조회

    void update(DiaryDTO diaryDTO); // 일기장 업데이트 (새로운 답장)

    void remove(String diaryID); // 일기장 삭제

    void modifyPartner(DiaryDTO diaryDTO); // 일기장 파트너 결정

    void modifyState(DiaryDTO diaryDTO); // 일기장 활성화 상태 변경 (비활성화)
}

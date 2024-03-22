package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.dto.DiaryDTO;

public interface DiaryService {

    String register(DiaryDTO diaryDTO); // 일기장 등록

    Diary readOne(String diaryID); // 일기장 조회

    void modify(DiaryDTO diaryDTO); // 일기장 수정

    void remove(String diaryID); // 일기장 삭제
}

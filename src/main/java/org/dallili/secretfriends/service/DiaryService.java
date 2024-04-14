package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.dto.DiaryDTO;

import java.util.List;
import java.util.UUID;

public interface DiaryService {

    Long addDiary(DiaryDTO diaryDTO); // 일기장 등록

    Long addKnownMatchingDiary(DiaryDTO diaryDTO);

    UUID addKnownsDiary(Long userID, String color);

    DiaryDTO findOne(Long diaryID); // 일기장 조회

    void modifyUpdate(Long diaryID, Long memberID); // 일기장 업데이트 (새로운 답장)

    void removeDiary(Long diaryID); // 일기장 삭제

    List<DiaryDTO> findAllDiaries(); //모든 일기장 목록 조회

    List<DiaryDTO> findStateDiaries(Long memberID, Boolean state); // 활성화 or 비활성화된 상태의 일기장 목록 조회

    List<DiaryDTO> findRepliedDiaries(Long loginMemberID); // 답장 온 일기장 목록 조회

    void modifyPartner(String code, Long partnerID); // 일기장 파트너 결정

    void modifyState(Long diaryID); // 일기장 비활성화

    UUID findCode(Long diaryID); //일기장 초대코드 조회

    DiaryDTO findDiaryByCode(String code); // 초대코드로 일기장 조회

    Diary findDiaryById(Long id);
}

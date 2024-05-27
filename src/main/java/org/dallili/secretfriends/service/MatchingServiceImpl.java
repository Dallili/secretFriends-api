package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Matching;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.dto.MatchingDTO;
import org.dallili.secretfriends.repository.MatchingRepository;
import org.dallili.secretfriends.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final float waitingPerHourPoint = 0.5f;
    private final float firstInterestPoint = 1f;
    private final float secondInterestPoint = 0.7f;
    private final float thirdInterestPoint = 0.4f;
    private final float scoreThreshold = 1;

    private final MatchingRepository matchingRepository;

    private final MemberService memberService;

    private final DiaryService diaryService;
    
    private final MatchingHistoryService matchingHistoryService;

    private final ModelMapper modelMapper;


    @Override
    public List<DiaryDTO.unKnownMatchingDiary> findUnknownDiary(Long memberID) {

        List<Matching> matchingList = matchingRepository.findAllByMemberID(memberID);
        int matchingCount = matchingList.size();
        List<DiaryDTO.unKnownMatchingDiary> diaryDTOs = new ArrayList<>();

        if(matchingCount > 0){
            for (int i=0; i<matchingCount; i++) {
                Random random = new Random();
                int nextInt = random.nextInt(0xffffff + 1);
                String randomColor = String.format("#%06x", nextInt);

                DiaryDTO.unKnownMatchingDiary diaryDTO = DiaryDTO.unKnownMatchingDiary.builder()
                        .memberID(memberID)
                        .color(randomColor)
                        .state(true)
                        .matchingID(matchingList.get(i).getMatchingID())
                        .build();
                diaryDTOs.add(diaryDTO);
            }
        }

        return diaryDTOs;
    }

    @Override
    public Long addMatching(MatchingDTO matchingDTO){

        Matching matching = modelMapper.map(matchingDTO, Matching.class);

        Long matchingID = matchingRepository.save(matching).getMatchingID();

        matchingRepository.flush();

        return matchingID;

    }

    @Override
    public Long removeMatching(Long matchingID){

        Matching matching = matchingRepository.findById(matchingID).get();

        Long memberID = matching.getMemberID();

        matchingRepository.deleteById(matching.getMatchingID());

        return memberID;

    }

    @Override
    public Map<String, Object> saveMatchingSearch(MatchingDTO.newMatching matching, Long newUserID) {


        MatchingDTO newMatching = MatchingDTO.builder()
                .firstInterest(matching.getFirstInterest())
                .secondInterest(matching.getSecondInterest())
                .thirdInterest(matching.getThirdInterest())
                .memberID(newUserID)
                .createdAt(matching.getCreatedAt())
                .build();

        Map<String, Object> result = new HashMap<>();

        List<Matching> matchingQueue = matchingRepository.findAll();

        float maxScore = Float.MIN_VALUE;
        int maxIndex = -1;

        // Matching queue에 등록된 지 요청들 중 궁합이 가장 좋은 요청과 매칭
        List<Float> scoreList = new ArrayList<>();

        if (!matchingQueue.isEmpty()) {
            // 매칭 점수 계산 (scoreList 완성)
            for (int i = 0; i < matchingQueue.size(); i++) {

                Matching oldMatching = matchingQueue.get(i);
                Long memberID = matchingQueue.get(i).getMemberID();

                // 자기 자신의 매칭 요청이면 패스
                // 이미 랜덤 매칭된 적 있는 상대면 패스
                if(matchingHistoryService.findHistory(memberID, newUserID) || (memberID.equals(newUserID)))
                {
                    continue;
                }

                // 1. 웨이팅 타임 점수
                LocalDateTime createdAt1 = oldMatching.getCreatedAt();
                LocalDateTime createdAt2 = newMatching.getCreatedAt();
                Duration duration = Duration.between(createdAt1, createdAt2);
                long hours = duration.toHours();
                float score = waitingPerHourPoint * hours;

                // 2. Interest 일치 점수
                if (newMatching.getFirstInterest().equals(oldMatching.getFirstInterest())){
                    score += firstInterestPoint * firstInterestPoint;
                }
                if ((newMatching.getFirstInterest().equals(oldMatching.getSecondInterest())) || (newMatching.getSecondInterest().equals(oldMatching.getFirstInterest()))) {
                    score += firstInterestPoint * secondInterestPoint;
                }
                if ((newMatching.getFirstInterest().equals(oldMatching.getSecondInterest())) || (newMatching.getThirdInterest().equals(oldMatching.getFirstInterest()))) {
                    score += firstInterestPoint * thirdInterestPoint;
                }
                if (newMatching.getSecondInterest().equals(oldMatching.getSecondInterest())) {
                    score += secondInterestPoint * secondInterestPoint;
                }
                if ((newMatching.getThirdInterest().equals(oldMatching.getSecondInterest())) || (newMatching.getSecondInterest().equals(oldMatching.getThirdInterest()))) {
                    score += secondInterestPoint * thirdInterestPoint;
                }
                if (newMatching.getThirdInterest().equals(oldMatching.getThirdInterest())) {
                    score += thirdInterestPoint * thirdInterestPoint;
                }

                log.info(oldMatching.getMatchingID()+"번 매칭과의 점수: "+score);

                scoreList.add(score);
            }

            // 매칭 시작
            while(!scoreList.isEmpty()){
                maxScore = Collections.max(scoreList);

                // 매칭 실패
                if(maxScore < scoreThreshold){
                    break;
                }

                // 매칭 성공 (일정 점수 이상 이어야 한다는 조건 통과)
                log.info("최종 최댓값: " + maxScore);
                maxIndex = scoreList.indexOf(maxScore);
                Long maxMatchingID = matchingQueue.get(maxIndex).getMatchingID();
                Long oldMemberID = matchingQueue.get(maxIndex).getMemberID();

                removeMatching(maxMatchingID); // 매칭 큐에서 삭제
                String oldMemberName = memberService.findMemberById(oldMemberID).getNickname();
                String newMemberName = memberService.findMemberById(newUserID).getNickname();

                Random random = new Random();
                int nextInt = random.nextInt(0xffffff + 1);
                String randomColor = String.format("#%06x", nextInt);

                DiaryDTO diaryDTO = DiaryDTO.builder()
                        .memberID(oldMemberID)
                        .partnerID(newUserID)
                        .memberName(oldMemberName)
                        .partnerName(newMemberName)
                        .updatedAt(LocalDateTime.now())
                        .updatedBy(newUserID)
                        .color(randomColor)
                        .build();

                Long diaryID = diaryService.addDiary(diaryDTO); // 다이어리 생성
                Long historyID = matchingHistoryService.addHistory(oldMemberID, newUserID); // 매칭히스토리 생성

                result.put("state", true);
                result.put("memberID", oldMemberID);
                result.put("memberName", oldMemberName);
                result.put("partnerID", newUserID);
                result.put("partnerName", newMemberName);
                result.put("diaryID", diaryID);
                return result;
            }

            // 매칭 실패 (점수 조건 통과 못함. 매칭큐에 추가해서 대기)
            Long newMatchingID = addMatching(newMatching);
            result.put("state", false);
            result.put("matchingID", newMatchingID);
            return result;

        } else { // 매칭 실패 (매칭 큐가 비어있음. 매칭큐에 추가해서 대기)
            Long newMatchingID = addMatching(newMatching);
            result.put("state", false);
            result.put("matchingID", newMatchingID);
            return result;

        }

    }

}

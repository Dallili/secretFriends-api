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
    public Long addMatching(MatchingDTO matchingDTO){

        Matching matching = modelMapper.map(matchingDTO, Matching.class);

        Long matchingID = matchingRepository.save(matching).getMatchingID();

        matchingRepository.flush();

        return matchingID;

    }

    @Override
    public Long removeMatching(Long matchingID){

        Long deletedMatchingID = matchingRepository.findById(matchingID).get().getMatchingID();

        matchingRepository.deleteById(matchingID);

        return deletedMatchingID;

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

            for (int i = 0; i < matchingQueue.size(); i++) {

                Matching oldMatching = matchingQueue.get(i);

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

            // 궁합 최대값
            maxScore = Collections.max(scoreList);
            log.info("최종 최댓값: " + maxScore);

            // 매칭 성공 (일정 점수 이상 이어야 한다는 조건 통과)
            if(maxScore > scoreThreshold){
                maxIndex = scoreList.indexOf(maxScore);
                Long maxMatchingID = matchingQueue.get(maxIndex).getMatchingID();

                Long oldMemberID = removeMatching(maxMatchingID); // 매칭 큐에서 삭제
                Long newMemberID = newMatching.getMemberID();
                String oldMemberName = memberService.findMemberById(oldMemberID).getNickname();
                String newMemberName = memberService.findMemberById(newMemberID).getNickname();

                DiaryDTO diaryDTO = DiaryDTO.builder()
                        .memberID(oldMemberID)
                        .partnerID(newMemberID)
                        .memberName(oldMemberName)
                        .partnerName(newMemberName)
                        .updatedAt(LocalDateTime.now())
                        .updatedBy(newMemberID)
                        .color("#000000")
                        .build();

                Long diaryID = diaryService.addDiary(diaryDTO); // 다이어리 생성
                Long historyID = matchingHistoryService.addHistory(oldMemberID, newMemberID); // 매칭히스토리 생성

                result.put("state", true);
                result.put("memberID", oldMemberID);
                result.put("memberName", oldMemberName);
                result.put("partnerID", newMemberID);
                result.put("partnerName", newMemberName);
                result.put("diaryID", diaryID);
                return result;

            } else { // 매칭 실패 (점수 조건 통과 못함. 매칭큐에 추가해서 대기)

                Long newMatchingID = addMatching(newMatching);
                result.put("state", false);
                result.put("matchingID", newMatchingID);

                return result;
            }

        } else { // 매칭 실패 (매칭 큐가 비어있음. 매칭큐에 추가해서 대기)

            Long newMatchingID = addMatching(newMatching);
            result.put("state", false);
            result.put("matchingID", newMatchingID);

            return result;

        }

    }

}

package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.MatchingHistory;
import org.dallili.secretfriends.dto.MatchingHistoryDTO;
import org.dallili.secretfriends.repository.MatchingHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MatchingHistoryServiceImpl implements MatchingHistoryService{

    final MatchingHistoryRepository matchingHistoryRepository;

    final ModelMapper modelMapper;

    @Override
    public Long addHistory(Long memberID, Long partnerID){

        MatchingHistoryDTO matchingHistoryDTO = MatchingHistoryDTO.builder()
                .memberID(memberID)
                .partnerID(partnerID)
                .build();

        MatchingHistory matchingHistory = modelMapper.map(matchingHistoryDTO, MatchingHistory.class);

        matchingHistoryRepository.save(matchingHistory);

        return matchingHistory.getHistoryID();
    }

    @Override
    public Boolean findHistory(Long memberID, Long partnerID){

        List<MatchingHistory> matchingHistories = matchingHistoryRepository.findAllByMemberID(String.valueOf(memberID)).stream()
                .filter(matchingHistory -> Integer.parseInt(matchingHistory.getPartnerID()) == partnerID)
                .collect(Collectors.toList());

        List<MatchingHistory> matchingHistories2 = matchingHistoryRepository.findAllByMemberID(String.valueOf(partnerID)).stream()
                .filter(matchingHistory -> Integer.parseInt(matchingHistory.getPartnerID()) == memberID)
                .collect(Collectors.toList());

        if (matchingHistories.isEmpty() && matchingHistories2.isEmpty()) return false;
        else return true;
    }
}

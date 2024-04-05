package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.MatchingHistory;
import org.dallili.secretfriends.dto.MatchingHistoryDTO;
import org.dallili.secretfriends.repository.MatchingHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MatchingHistoryServiceImpl implements MatchingHistoryService{

    final MatchingHistoryRepository matchingHistoryRepository;

    final ModelMapper modelMapper;

    @Override
    public Long addHistory(String memberID, String partnerID){

        MatchingHistoryDTO matchingHistoryDTO = MatchingHistoryDTO.builder()
                .memberID(memberID)
                .partnerID(partnerID)
                .build();

        MatchingHistory matchingHistory = modelMapper.map(matchingHistoryDTO, MatchingHistory.class);

        matchingHistoryRepository.save(matchingHistory);

        return matchingHistory.getHistoryID();
    }
}

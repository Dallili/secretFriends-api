package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Matching;
import org.dallili.secretfriends.dto.MatchingDTO;
import org.dallili.secretfriends.repository.MatchingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final MatchingRepository matchingRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long addMatching(MatchingDTO matchingDTO){

        Matching matching = modelMapper.map(matchingDTO, Matching.class);

        Long matchingID = matchingRepository.save(matching).getMatchingID();

        matchingRepository.flush();

        return matchingID;

    }

    @Override
    public void removeMatching(Long matchingID){

        matchingRepository.deleteById(matchingID);

    }
}

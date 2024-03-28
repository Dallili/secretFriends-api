package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.dto.EntryDTO;
import org.dallili.secretfriends.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long addEntry(EntryDTO entryDTO) {
        Entry entry = modelMapper.map(entryDTO, Entry.class);
        Long eid = entryRepository.save(entry).getEntryID();
        return eid;
    }

    @Override
    public Boolean modifyState(Long entryID) {
        Optional<Entry> entryOptional = entryRepository.findById(entryID);
        Entry entry = entryOptional.orElseThrow();
        if(entry.getState().equals("N")){
            entryRepository.updateState(entryID);
            entryRepository.updateSendAt(entryID, LocalDateTime.now());
            return true;
        } else
            return false;
    }
}
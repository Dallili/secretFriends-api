package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.EntryDTO;

import java.util.List;

@Transactional
public interface EntryService {
    Long addEntry(EntryDTO entryDTO);
    Boolean modifyState(Long entryID);
    List<EntryDTO.Response> findEntry(String diaryID);
}

package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.EntryDTO;

import java.util.List;

@Transactional
public interface EntryService {
    Long addEntry(EntryDTO entryDTO);
    Boolean modifyState(Long entryID);
    EntryDTO.UnsentEntryResponse modifyContent(EntryDTO.ModifyRequest entryDTO);
    List<EntryDTO.SentEntryResponse> findSentEntry(String diaryID);
    List<EntryDTO.UnsentEntryResponse> findUnsentEntry(String diaryID);
}

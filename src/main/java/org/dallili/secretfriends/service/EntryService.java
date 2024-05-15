package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.dto.EntryDTO;

import java.util.List;
import java.util.Map;

@Transactional
public interface EntryService {
    Long addEntry(EntryDTO.CreateRequest entryDTO);
    Boolean modifyState(Long entryID, Long memberID);
    EntryDTO.UnsentEntryResponse modifyContent(EntryDTO.ModifyRequest entryDTO);
    List<EntryDTO.SentEntryResponse> findSentEntry(Long diaryID);
    List<EntryDTO.UnsentEntryResponse> findUnsentEntry(Long diaryID);
    Entry findEntryById(Long entryID);
    List<EntryDTO.SentEntryResponse> modifyTextFiltering(List<EntryDTO.SentEntryResponse> entry);
    Long findOpponent(Long entryID, Long memberID);
    Long findDiaryID(Long entryID);

}

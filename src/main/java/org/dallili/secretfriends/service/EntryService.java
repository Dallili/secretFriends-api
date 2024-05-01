package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
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
    Boolean findMemberUseFiltering (Long memberID);
    List<EntryDTO.SentEntryResponse> modifyTextFiltering(List<EntryDTO.SentEntryResponse> entry);

}

package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.EntryDTO;

@Transactional
public interface EntryService {
    Long addEntry(EntryDTO entryDTO);
    Boolean modifyState(Long entryID);
}

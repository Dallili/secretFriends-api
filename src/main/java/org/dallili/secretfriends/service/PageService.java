package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import org.dallili.secretfriends.dto.PageDTO;

@Transactional
public interface PageService {
    Long addPage(PageDTO pageDTO);
    Boolean modifyState(Long pageID);
}

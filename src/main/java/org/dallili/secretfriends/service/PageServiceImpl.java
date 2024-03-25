package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Page;
import org.dallili.secretfriends.dto.PageDTO;
import org.dallili.secretfriends.repository.PageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PageServiceImpl implements PageService{

    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long addPage(PageDTO pageDTO) {
        Page page = modelMapper.map(pageDTO,Page.class);
        Long pid = pageRepository.save(page).getPageID();
        return pid;
    }
}

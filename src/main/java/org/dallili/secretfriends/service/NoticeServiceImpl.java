package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.domain.Notice;
import org.dallili.secretfriends.dto.NoticeDTO;
import org.dallili.secretfriends.repository.NoticeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long addNotice(NoticeDTO.CreateRequest request) {
        Notice notice = modelMapper.map(request, Notice.class);
        Long noticeID = noticeRepository.save(notice).getNoticeID();
        return noticeID;
    }

    @Override
    public List<NoticeDTO.ListResponse> findNoticeList() {
        return null;
    }

    @Override
    public NoticeDTO.DetailsResponse findNoticeDetails(Long noticeID) {
        return null;
    }
}

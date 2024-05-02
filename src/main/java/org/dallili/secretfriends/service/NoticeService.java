package org.dallili.secretfriends.service;

import org.dallili.secretfriends.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    public Long addNotice(NoticeDTO.CreateRequest request);
    public List<NoticeDTO.ListResponse> findNoticeList();
    public NoticeDTO.DetailsResponse findNoticeDetails(Long noticeID);
}

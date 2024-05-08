package org.dallili.secretfriends.notify.service;

import org.dallili.secretfriends.notify.dto.NotifyDTO;

import java.util.List;

public interface NotifyService {

    // notify 테이블에 알림 데이터 저장" 담당하는 메소드
    void saveNotifyTable(Long receiverID, Long senderID, NotifyDTO.NotifyType type);
    void removeNotify(Long notifyID);
    List<NotifyDTO> findAllNotify(Long receiverID);

}

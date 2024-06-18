package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.service.NotifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class NotifyServiceTests {

    @Autowired
    private NotifyService notifyService;
    @Test
    public void testSaveNotify() {

        notifyService.saveNotifyTable(1L, 1L, 2L, NotifyDTO.NotifyType.INACTIVATE);
    }

    @Test
    public void testRemoveNotify(){
        notifyService.removeNotify(3L);

    }

    @Test
    public void testFindAllNotify() {
        log.info(notifyService.findAllNotify(1L));
    }
}

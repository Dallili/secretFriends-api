package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.PageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class PageServiceTests {

    @Autowired
    private PageService pageService;

    @Test
    public void testAddPage(){

        PageDTO pageDTO = PageDTO.builder()
                .diaryID("diary1")
                .writer("user1")
                .text("일기")
                .build();

        Long pid = pageService.addPage(pageDTO);

        log.info(pid);
    }

    @Test
    public void testModifyState(){
        Long pid = 40L;
        Boolean result = pageService.modifyState(pid);
        log.info(result);
    }

}

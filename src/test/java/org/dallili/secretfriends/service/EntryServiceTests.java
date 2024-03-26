package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.EntryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class EntryServiceTests {

    @Autowired
    private EntryService entryService;

    @Test
    public void testAddEntry(){

        EntryDTO entryDTO = EntryDTO.builder()
                .diaryID("diary1")
                .writer("user1")
                .text("일기")
                .build();

        Long eid = entryService.addEntry(entryDTO);

        log.info(eid);
    }

    @Test
    public void testModifyState(){
        Long eid = 1L;
        Boolean result = entryService.modifyState(eid);
        log.info(result);
    }

}

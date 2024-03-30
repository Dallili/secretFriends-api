package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.EntryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
                .content("일기")
                .build();

        Long eid = entryService.addEntry(entryDTO);

        log.info(eid);
    }

    @Test
    public void testModifyContent(){
        EntryDTO.ModifyRequest req = EntryDTO.ModifyRequest.builder()
                .entryID(8L)
                .content("수정된 일기")
                .build();
        EntryDTO.Response res = entryService.modifyContent(req);
        log.info(res);
    }

    @Test
    public void testModifyState(){
        Long eid = 1L;
        Boolean result = entryService.modifyState(eid);
        log.info(result);
    }

    @Test
    public void testFindEntry(){
        String diaryID = "diary1";
        List<EntryDTO.Response> dto = entryService.findEntry(diaryID);
        dto.stream().forEach(i -> log.info(i));
    }
}

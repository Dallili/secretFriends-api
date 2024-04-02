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
                .entryID(11L)
                .content("수정된 일기")
                .build();
        EntryDTO.UnsentEntryResponse res = entryService.modifyContent(req);
        log.info(res);
    }

    @Test
    public void testModifyState(){
        Long eid = 12L;
        Boolean result = entryService.modifyState(eid);
        log.info(result);
    }

    @Test
    public void testFindSentEntry(){
        String diaryID = "diary1";
        List<EntryDTO.SentEntryResponse> dto = entryService.findSentEntry(diaryID);
        dto.stream().forEach(i -> log.info(i));
    }
    @Test
    public void testFindUnsentEntry(){
        String diaryID = "diary1";
        List<EntryDTO.UnsentEntryResponse> dto = entryService.findUnsentEntry(diaryID);
        dto.stream().forEach(i -> log.info(i));
    }
}

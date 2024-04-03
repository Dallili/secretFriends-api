package org.dallili.secretfriends.service;

import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.dto.EntryDTO;
import org.dallili.secretfriends.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Log4j2
public class EntryServiceTests {

    @Autowired
    private EntryService entryService;
    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void testAddEntry(){
        EntryDTO entryDTO = EntryDTO.builder()
                .diaryID("diary2")
                .writer("user2")
                .content("일기")
                .build();

        Long eid = entryService.addEntry(entryDTO);

        Entry entry = entryRepository.findById(eid).get();
        log.info(eid);

        assertThat(entry.getEntryID()).isEqualTo(eid);
        assertThat(entry.getDiary().getDiaryID()).isEqualTo("diary2");
        assertThat(entry.getWriter()).isEqualTo("user2");
        assertThat(entry.getContent()).isEqualTo("일기");
    }

    @Test
    public void testModifyContent(){
        EntryDTO.ModifyRequest req = EntryDTO.ModifyRequest.builder()
                .entryID(3L)
                .content("수정된 일기")
                .build();
        EntryDTO.UnsentEntryResponse res = entryService.modifyContent(req);
        log.info(res);

        assertThat(res.getEntryID()).isEqualTo(2L);
        assertThat(res.getContent()).isEqualTo("수정된 일기");
    }

    @Test
    public void testModifyState(){
        Long eid = 4L;
        Boolean result = entryService.modifyState(eid);
        log.info(result);
    }

    @Test
    public void testFindSentEntry(){
        String diaryID = "diary2";
        List<EntryDTO.SentEntryResponse> dto = entryService.findSentEntry(diaryID);
        dto.stream().forEach(i -> log.info(i));
    }
    @Test
    public void testFindUnsentEntry(){
        String diaryID = "diary2";
        List<EntryDTO.UnsentEntryResponse> dto = entryService.findUnsentEntry(diaryID);
        dto.stream().forEach(i -> log.info(i));
    }
}

package org.dallili.secretfriends.service;

import com.vane.badwordfiltering.BadWordFiltering;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.dto.EntryDTO;
import org.dallili.secretfriends.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final ModelMapper modelMapper;
    private final DiaryService diaryService;
    private final MemberService memberService;

    @Override
    public Long addEntry(EntryDTO.CreateRequest entryDTO) {
        Diary diary = diaryService.findDiaryById(entryDTO.getDiaryID());

        if(!(entryDTO.getWriterID()==diary.getMember().getMemberID()||entryDTO.getWriterID()==diary.getPartner().getMemberID())){
            throw new IllegalArgumentException("작성 권한이 없는 일기장입니다.");
        }

        Member member = memberService.findMemberById(entryDTO.getWriterID());

        Entry entry = entryDTO.toEntity(diary,member);
        Long eid = entryRepository.save(entry).getEntryID();
        return eid;
    }

    @Override
    @Transactional
    public Boolean modifyState(Long entryID, Long memberID) {
        Entry entry = entryRepository.findById(entryID).orElseThrow(()->{
            throw new EntityNotFoundException("존재하지 않는 일기입니다.");
        });

        if(!(entry.getMember().getMemberID().equals(memberID))){
            throw new IllegalArgumentException("전달 권한이 없는 일기입니다.");
        }

        if(entry.getState().equals("N")){
            entryRepository.updateState(entryID);
            entryRepository.updateSendAt(entryID, LocalDateTime.now());
            diaryService.modifyUpdate(entry.getDiary().getDiaryID(),memberID);
            return true;
        } else
            return false;
    }

    @Override
    public EntryDTO.UnsentEntryResponse modifyContent(EntryDTO.ModifyRequest entryDTO) {
        Entry entry = entryRepository.findById(entryDTO.getEntryID()).orElseThrow(()->{
            throw new EntityNotFoundException("존재하지 않는 일기입니다.");
        });

        entry.changeContent(entryDTO.getContent());
        entryRepository.save(entry);
        entryRepository.flush();

        EntryDTO.UnsentEntryResponse response = entry.toUnsentDto();
        return response;
    }

    @Override
    public List<EntryDTO.SentEntryResponse> findSentEntry(Long diaryID) {
        List<Entry> entries = entryRepository.selectEntry(diaryID,"Y");

        List<EntryDTO.SentEntryResponse> dto = entries.stream().map(entry -> entry.toSentDto() ).collect(Collectors.toList());

        return dto;
    }

    @Override
    public List<EntryDTO.UnsentEntryResponse> findUnsentEntry(Long diaryID) {
        List<Entry> entries = entryRepository.selectEntry(diaryID,"N");

        List<EntryDTO.UnsentEntryResponse> dto = entries.stream().map(entry -> entry.toUnsentDto()).collect(Collectors.toList());

        return dto;
    }
    public List<EntryDTO.SentEntryResponse> modifyTextFiltering(List<EntryDTO.SentEntryResponse> entry){

        BadWordFiltering badWordFiltering = new BadWordFiltering();
        for (EntryDTO.SentEntryResponse sentEntry : entry) {
            String filteredText = badWordFiltering.change(sentEntry.getContent());
            sentEntry.changeContent(filteredText);
        }
        return entry;
    }
}

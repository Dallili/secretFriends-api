package org.dallili.secretfriends.service;

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
import java.util.List;
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
    public Boolean modifyState(Long entryID) {
        Optional<Entry> entryOptional = entryRepository.findById(entryID);
        Entry entry = entryOptional.orElseThrow();
        if(entry.getState().equals("N")){
            entryRepository.updateState(entryID);
            entryRepository.updateSendAt(entryID, LocalDateTime.now());
            return true;
        } else
            return false;
    }

    @Override
    public EntryDTO.UnsentEntryResponse modifyContent(EntryDTO.ModifyRequest entryDTO) {
        Optional<Entry> entryOptional = entryRepository.findById(entryDTO.getEntryID());
        Entry entry = entryOptional.orElseThrow();

        entry.changeContent(entryDTO.getContent());
        entryRepository.save(entry);
        entryRepository.flush();

        EntryDTO.UnsentEntryResponse response = modelMapper.map(entry,EntryDTO.UnsentEntryResponse.class);
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
}

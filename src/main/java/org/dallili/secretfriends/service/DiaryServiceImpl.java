package org.dallili.secretfriends.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final ModelMapper modelMapper;

    private final DiaryRepository diaryRepository;

    private final MemberService memberService;

    @Override
    public Long addDiary(DiaryDTO diaryDTO) {

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        Long diaryID = diaryRepository.save(diary).getDiaryID();

        return diaryID;
    }

    @Override
    public Long addKnownMatchingDiary(DiaryDTO diaryDTO){

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        UUID code = UUID.randomUUID();

        diary.makeCode(code);

        Long diaryID = diaryRepository.save(diary).getDiaryID();

        diaryRepository.flush();

        return diaryID;

    }

    @Override
    public UUID addKnownsDiary(Long memberID, String color){

        UUID code = UUID.randomUUID();

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .memberID(memberID)
                .memberName(memberService.findMemberById(memberID).getNickname())
                .color(color)
                .code(code)
                .build();

        //log.info(diaryDTO);

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        diaryRepository.save(diary);

        return code;

    }


    @Override
    public DiaryDTO findOne(Long diaryID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);

        return diaryDTO;

    }

    @Override
    public void modifyUpdate(Long diaryID, Long memberID) { //일기 전송 후 작동해야하는 메소드.
        // 마지막 작성자와 마지막 작성일을 수정한다.

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        //log.info("업데이트 전: " + diary);

        diary.updateDiary(memberID, LocalDateTime.now());

        diaryRepository.save(diary);

        //log.info("업데이트 후: " + diary);


    }


    @Override
    public void removeDiary(Long diaryID) {

        diaryRepository.deleteById(diaryID);

    }



    @Override
    public void modifyPartner(String code, Long partnerID){

        UUID uuidCode = UUID.fromString(code);

        Optional<Diary> result = diaryRepository.findByCode(uuidCode);

        Diary diary = result.orElseThrow();

        Member partner = memberService.findMemberById(partnerID);

        diary.decidePartner(partner);

        diaryRepository.save(diary);
    }

    @Override
    public void modifyState(Long diaryID) {

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        diary.changeState(false);

        diaryRepository.save(diary);

    }

    @Override
    public List<DiaryDTO> findAllDiaries() {

        List<Diary> diaries = diaryRepository.findAll();

        return diaries.stream()
                .map(diary -> modelMapper.map(diary, DiaryDTO.class) )
                .collect(Collectors.toList());

    }

    @Override
    public List<DiaryDTO> findStateDiaries(Long memberID, Boolean state) {
        List<Diary> diaries = diaryRepository.findAll();

        return diaries.stream()
                .filter(diary -> diary.isState() == state)
                .filter(diary -> {
                    if (diary.getPartner() != null) {
                        return memberID.equals(diary.getMember().getMemberID()) ||
                                memberID.equals(diary.getPartner().getMemberID());
                    } else {
                        return memberID.equals(diary.getMember().getMemberID());
                    }
                })
                .map(diary -> modelMapper.map(diary, DiaryDTO.class))
                .collect(Collectors.toList());
    }

  
    @Override
    public List<DiaryDTO> findRepliedDiaries(Long loginMemberID){

        List<Diary> diaries = diaryRepository.findAll();

        return diaries.stream()
                .filter(diary -> diary.isState() == true)
                .filter(diary -> diary.getPartner()!=null)
                .filter(diary -> loginMemberID.equals(diary.getMember().getMemberID()) || loginMemberID.equals(diary.getPartner().getMemberID()))
                .filter(diary -> diary.getUpdatedBy()!=null)
                .filter(diary -> !loginMemberID.equals(diary.getUpdatedBy()))
                .map(diary -> modelMapper.map(diary, DiaryDTO.class))
                .collect(Collectors.toList());


    }

    @Override
    public UUID findCode(Long diaryID) {

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        return diary.getCode();

    }

    @Override
    public DiaryDTO findDiaryByCode(String code){

        UUID uuidCode = UUID.fromString(code);

        Optional<Diary> result = diaryRepository.findByCode(uuidCode);

        Diary diary = result.orElseThrow();

        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);

        return diaryDTO;

    }

    @Override
    public Diary findDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(()->{
            return new EntityNotFoundException(id + ": 존재하지 않는 일기장입니다.");
        });
        return diary;
    }

    @Override
    public void modifyDiaryTakeBack(Long diaryID, Long memberID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        LocalDateTime lastUpdated = diary.getUpdatedAt();
        LocalDateTime now = LocalDateTime.now();

        if (ChronoUnit.DAYS.between(lastUpdated, now) >= 14) {
            modifyUpdate(diaryID, memberID);
        } else {
            throw new IllegalStateException("아직 회수할 수 없습니다.");
        }

    }
}

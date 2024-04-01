package org.dallili.secretfriends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.User;
import org.dallili.secretfriends.dto.DiaryDTO;
import org.dallili.secretfriends.dto.UserDTO;
import org.dallili.secretfriends.repository.DiaryRepository;
import org.dallili.secretfriends.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final ModelMapper modelMapper;

    private final DiaryRepository diaryRepository;

    private final UserRepository userRepository;

    @Override
    public String addDiary(DiaryDTO diaryDTO) {

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        String diaryID = diaryRepository.save(diary).getDiaryID();

        return diaryID;
    }


    @Override
    public DiaryDTO findOne(String diaryID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);

        return diaryDTO;

    }

    @Override
    public void modifyUpdate(DiaryDTO diaryDTO) { //일기 전송 후 작동해야하는 메소드.
        // 마지막 작성자와 마지막 작성일을 수정한다.

        Optional<Diary> result = diaryRepository.findById(diaryDTO.getDiaryID());

        Diary diary = result.orElseThrow();

        //log.info("업데이트 전: " + diary);

        diary.updateDiary(diaryDTO.getUpdatedBy(), LocalDateTime.now());

        diaryRepository.save(diary);

        //log.info("업데이트 후: " + diary);


    }


    @Override
    public void removeDiary(String diaryID) {

        diaryRepository.deleteById(diaryID);

    }



    @Override
    public void modifyPartner(String diaryID, String partnerID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        Optional<User> partnerResult = userRepository.findById(partnerID);

        User partner = partnerResult.orElseThrow();

        diary.decidePartner(partner);

        diaryRepository.save(diary);
    }

    @Override
    public void modifyState(String diaryID) {

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
    public List<DiaryDTO> findStateDiaries(String userID, Boolean state) {

        List<Diary> diaries = diaryRepository.findAll();

        return diaries.stream()
                .filter(diary -> diary.isActivated() == state)
                .filter(diary -> userID.equals(diary.getUser().getUserID()) || userID.equals(diary.getPartner().getUserID()))
                .map(diary -> modelMapper.map(diary, DiaryDTO.class) )
                .collect(Collectors.toList());

    }

}

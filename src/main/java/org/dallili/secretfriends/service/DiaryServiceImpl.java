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

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final ModelMapper modelMapper;

    private final DiaryRepository diaryRepository;

    private final UserRepository userRepository;

    @Override
    public String register(DiaryDTO diaryDTO) {

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        String diaryID = diaryRepository.save(diary).getDiaryID();

        return diaryID;
    }


    @Override
    public DiaryDTO readOne(String diaryID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);

        //좀 더 개발 후에 pageDTO도 함께 다뤄야 함
        return diaryDTO;

    }

    @Override
    public void update(DiaryDTO diaryDTO) { //일기 전송 후 작동해야하는 메소드.
        // 마지막 작성자와 마지막 작성일을 수정한다.

        Optional<Diary> result = diaryRepository.findById(diaryDTO.getDiaryID());

        Diary diary = result.orElseThrow();

        //log.info("업데이트 전: " + diary);

        diary.updateDiary(diaryDTO.getUpdatedBy(), diaryDTO.getUpdatedAt());

        diaryRepository.save(diary);

        //log.info("업데이트 후: " + diary);


    }


    @Override
    public void remove(String diaryID) {

        diaryRepository.deleteById(diaryID);

    }



    @Override
    public void modifyPartner(String diaryID, String partnerID){

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        Optional<User> partnerResult = userRepository.findById(partnerID);

        User partner = partnerResult.orElseThrow();

        diary.setPartner(partner);

        diaryRepository.save(diary);
    }

    @Override
    public void modifyState(String diaryID) {

        Optional<Diary> result = diaryRepository.findById(diaryID);

        Diary diary = result.orElseThrow();

        diary.setActivated(false);

        diaryRepository.save(diary);

    }

}

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
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private ModelMapper modelMapper;

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
    public void update(DiaryDTO diaryDTO) {

        Optional<Diary> result = diaryRepository.findById(diaryDTO.getDiaryID());

        Diary diary = result.orElseThrow();

        diary.updateDiary(diaryDTO.getUpdatedBy(), diaryDTO.getUpdatedAt());

        diaryRepository.save(diary);


    }


    @Override
    public void remove(String diaryID) {

        diaryRepository.deleteById(diaryID);

    }

    @Override
    public void modifyPartner(DiaryDTO diaryDTO){

        Optional<Diary> result = diaryRepository.findById(diaryDTO.getDiaryID());

        Diary diary = result.orElseThrow();

        Optional<User> partnerResult = userRepository.findById(diaryDTO.getPartnerID());

        User partner = partnerResult.orElseThrow();

        diary.decidePartner(partner);

        diaryRepository.save(diary);
    }

    @Override
    public void modifyState(DiaryDTO diaryDTO){

        Optional<Diary> result = diaryRepository.findById(diaryDTO.getDiaryID());

        Diary diary = result.orElseThrow();

        diary.changeState(diaryDTO.isActivated());

        diaryRepository.save(diary);

    }

}

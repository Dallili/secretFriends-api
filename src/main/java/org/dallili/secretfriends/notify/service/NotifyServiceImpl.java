package org.dallili.secretfriends.notify.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.NotifyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService{

    private final NotifyRepository notifyRepository;
    private final ModelMapper modelMapper;

    public void saveNotifyTable(Long diaryID, Long receiverID, Long senderID, NotifyDTO.NotifyType type){

        NotifyDTO receiverNotifyDTO = NotifyDTO.builder()
                .notifyType(type)
                .receiverID(receiverID)
                .senderID(senderID)
                .diaryID(diaryID)
                .build();
        log.info(receiverNotifyDTO);
        Notify receiverNotify = modelMapper.map(receiverNotifyDTO, Notify.class);
        log.info(receiverNotify);
        notifyRepository.save(receiverNotify);

        if(type == NotifyDTO.NotifyType.NEWDIARY || type == NotifyDTO.NotifyType.INACTIVATE){
            NotifyDTO senderNotifyDTO = NotifyDTO.builder()
                    .notifyType(type)
                    .receiverID(senderID)
                    .senderID(receiverID)
                    .diaryID(diaryID)
                    .build();
        Notify senderNotify = modelMapper.map(senderNotifyDTO, Notify.class);
        notifyRepository.save(senderNotify);
        }
    }

    public void removeNotify(Long notifyID){
        notifyRepository.deleteById(notifyID);
    }

    @Transactional
    public List<NotifyDTO.notifyResponse> findAllNotify(Long receiverID) {
        List<Notify> notifyList = notifyRepository.findAllByReceiver_MemberID(receiverID);

        List<NotifyDTO.notifyResponse> notifyResponseList = notifyList.stream()
                .map(notify -> {
                    NotifyDTO.notifyResponse notifyResponse = NotifyDTO.notifyResponse.builder()
                            .notifyID(notify.getNotifyID())
                            .notifyType(notify.getNotifyType())
                            .receiverNickname(notify.getReceiver().getNickname())
                            .senderNickname(notify.getSender().getNickname())
                            .updatedAt(notify.getUpdatedAt())
                            .diaryColor(notify.getDiary().getColor())
                            .build();
                    return notifyResponse;
                })
                .collect(Collectors.toList());

        return notifyResponseList;
    }
}

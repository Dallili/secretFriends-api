package org.dallili.secretfriends.notify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.NotifyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService{

    private final NotifyRepository notifyRepository;
    private final ModelMapper modelMapper;

    public void saveNotifyTable(Long receiverID, Long senderID, NotifyDTO.NotifyType type){

        NotifyDTO receiverNotifyDTO = NotifyDTO.builder()
                .notifyType(type)
                .updatedAt(LocalDateTime.now())
                .receiverID(receiverID)
                .senderID(senderID)
                .build();
        log.info(receiverNotifyDTO);
        Notify receiverNotify = modelMapper.map(receiverNotifyDTO, Notify.class);
        log.info(receiverNotify);
        notifyRepository.save(receiverNotify);

        if(type == NotifyDTO.NotifyType.NEWDIARY || type == NotifyDTO.NotifyType.INACTIVATE){
            NotifyDTO senderNotifyDTO = NotifyDTO.builder()
                    .notifyType(type)
                    .updatedAt(LocalDateTime.now())
                    .receiverID(senderID)
                    .senderID(receiverID)
                    .build();
        Notify senderNotify = modelMapper.map(senderNotifyDTO, Notify.class);
        notifyRepository.save(senderNotify);
        }
    }

    public void removeNotify(Long notifyID){
        notifyRepository.deleteById(notifyID);
    }

    public List<NotifyDTO> findAllNotify(Long receiverID){
        List<Notify> notifyList = notifyRepository.findAllByReceiver_MemberID(receiverID);

        List<NotifyDTO> notifyDTOList = notifyList.stream()
                .map(notify -> {
                    NotifyDTO notifyDTO = modelMapper.map(notify, NotifyDTO.class);
                    // notificationType 가공하여 NotifyDTO에 추가
                    notifyDTO.setNotifyType(NotifyDTO.NotifyType.valueOf(notify.getNotifyType()));
                    return notifyDTO;
                })
                .collect(Collectors.toList());

        return notifyDTOList;
    }
}

package org.dallili.secretfriends.notify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.notify.domain.Notify;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.repository.NotifyRepository;
import org.dallili.secretfriends.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService{

    private final NotifyRepository notifyRepository;
    private final ModelMapper modelMapper;
    private final MemberService memberService;

    public void saveNotifyTable(Long receiverID, Long senderID, NotifyDTO.NotifyType type){

        Member receiver = memberService.findMemberById(receiverID);
        Member sender = memberService.findMemberById(senderID);

        Notify notify = Notify.builder()
                .notifyType(type.toString())
                .receiver(receiver)
                .sender(sender)
                .updatedAt(LocalDateTime.now())
                .build();

        notifyRepository.save(notify);
    }

    public void removeNotify(Long notifyID){
        notifyRepository.deleteById(notifyID);
    }
}

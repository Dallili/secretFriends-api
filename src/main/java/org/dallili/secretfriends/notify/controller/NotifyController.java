package org.dallili.secretfriends.notify.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.notify.dto.NotifyDTO;
import org.dallili.secretfriends.notify.service.NotifyService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotifyController {

    private final NotifyService notifyService;
    @Operation(summary = "Notify List GET", description = "알림 목록 조회")
    @GetMapping(value = "/")
    public List<NotifyDTO> notifyDTOList (Authentication authentication){
        return notifyService.findAllNotify(Long.parseLong(authentication.getName()));
    }


}

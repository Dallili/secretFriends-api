package org.dallili.secretfriends.email.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.email.dto.EmailDTO;
import org.dallili.secretfriends.email.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/members/signup/email")
    public void sendVerificationEmail(@RequestBody @Valid EmailDTO.SendRequest request) throws MessagingException {
        emailService.sendVerificationEmail(request.getEmail());
    }

    @DeleteMapping("/members/signup/email")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEmailByCode(@RequestBody @Valid EmailDTO.VerifyRequest request){
        LocalDateTime now = LocalDateTime.now();
        emailService.verifyCode(request.getCode(), now);
    }

}

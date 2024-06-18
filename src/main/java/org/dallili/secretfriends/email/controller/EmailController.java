package org.dallili.secretfriends.email.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "회원가입 이메일 인증 코드 발송", description = "이메일을 받아 해당 메일로 인증 코드 발송")
    public void sendVerificationEmail(@RequestBody @Valid EmailDTO.SendRequest request) throws MessagingException {
        emailService.sendVerificationEmail(request.getEmail());
    }

    @PostMapping("/members/signup/email/verification")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "인증 코드 증명", description = "사용자로부터 발송한 인증 코드를 입력받아서 해당 코드가 유효한지 확인")
    public void verifyEmailByCode(@RequestBody @Valid EmailDTO.VerifyRequest request){
        LocalDateTime now = LocalDateTime.now();
        emailService.verifyCode(request.getCode(), now);
    }

}

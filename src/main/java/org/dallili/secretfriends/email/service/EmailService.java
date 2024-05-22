package org.dallili.secretfriends.email.service;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.email.dto.VerificationCode;
import org.dallili.secretfriends.email.repository.VerificationCodeRepository;
import org.dallili.secretfriends.exception.CustomException;
import org.dallili.secretfriends.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${mail.username}")
    private String serviceEmail;
    private final Integer EXPIRATION_TIME_MINUTES = 1;
    private final JavaMailSender javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public void sendVerificationEmail(String to){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(serviceEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(String.format("Email Verification For %s", to));

        VerificationCode verificationCode = generateVerificationCode();
        verificationCodeRepository.save(verificationCode);

        String text = verificationCode.getMessage();
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }

    private VerificationCode generateVerificationCode(){
        String code = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        return VerificationCode.builder()
                .code(code)
                .createdAt(now)
                .expirationTimeMinutes(EXPIRATION_TIME_MINUTES)
                .build();
    }

    public void verifyCode(String code, LocalDateTime verifiedAt){
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code).orElseThrow(()->{
            throw new CustomException(ErrorCode.EMAIL_VERIFICATION_FAILED);
        });

        if(verificationCode.isExpired(verifiedAt)){
            throw new CustomException(ErrorCode.EMAIL_VERIFICATION_EXPIRED);
        }

        verificationCodeRepository.remove(verificationCode);
    }
}

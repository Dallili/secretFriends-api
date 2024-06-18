package org.dallili.secretfriends.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.email.dto.VerificationCode;
import org.dallili.secretfriends.email.repository.VerificationCodeRepository;
import org.dallili.secretfriends.exception.CustomException;
import org.dallili.secretfriends.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${mail.username}")
    private String serviceEmail;
    @Value("${mail.templates.img.logo}")
    private String logoPath;
    @Value("${mail.templates.img.title}")
    private String titlePath;
    @Value("${mail.templates.img.text}")
    private String textPath;

    private final Integer EXPIRATION_TIME_MINUTES = 3;

    private final JavaMailSender javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    private final SpringTemplateEngine templateEngine;

    public void sendVerificationEmail(String to) throws MessagingException {

        VerificationCode verificationCode = generateVerificationCode();
        verificationCodeRepository.save(verificationCode);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", verificationCode.getCode());
        map.put("expirationTime", String.format("위 코드의 만료 시간은 %s 입니다.",verificationCode.getExpirationTime()));

        Context context = new Context();
        context.setVariables(map); //템플릿에 전달할 데이터
        String html = templateEngine.process("verificationCode.html",context);


        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true,"UTF-8");
        helper.setFrom(serviceEmail);   //이메일 발신 주소
        helper.setTo(to);   //이메일 송신 주소
        helper.setSubject("[비밀친구] 회원가입 정보 인증을 위한 메일입니다.");   //이메일 제목
        helper.setText(html, true);
        helper.addInline("logo",new ClassPathResource(logoPath));
        helper.addInline("title",new ClassPathResource(titlePath));
        helper.addInline("text",new ClassPathResource(textPath));
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

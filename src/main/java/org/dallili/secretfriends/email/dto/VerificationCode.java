package org.dallili.secretfriends.email.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class VerificationCode {
    private String code;
    private LocalDateTime createdAt;
    private Integer expirationTimeMinutes;

    //만료되었는지 확인
    public boolean isExpired(LocalDateTime now){
        LocalDateTime expiredAt = this.createdAt.plusMinutes(this.expirationTimeMinutes);
        return now.isAfter(expiredAt);
    }

    public String getMessage(){
        String expiredAt = this.createdAt
                .plusMinutes(this.expirationTimeMinutes)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format(
                """
                        [Verification Code]
                        %s
                        만료 시간: %s
                        """,
                this.code, expiredAt
        );
    }
}

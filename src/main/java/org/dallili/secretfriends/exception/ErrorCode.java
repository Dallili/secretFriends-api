package org.dallili.secretfriends.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_VERIFICATION_FAILED(420,"인증 코드가 틀렸습니다."),
    EMAIL_VERIFICATION_EXPIRED(421,"인증 코드가 만료되었습니다.");

    private final int status;
    private final String message;
}

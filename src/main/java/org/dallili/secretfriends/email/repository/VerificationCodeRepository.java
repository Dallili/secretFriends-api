package org.dallili.secretfriends.email.repository;

import org.dallili.secretfriends.email.dto.VerificationCode;

import java.util.Optional;

public interface VerificationCodeRepository {
    public VerificationCode save(VerificationCode verificationCode);
    public Optional<VerificationCode> findByCode(String code);
    public void remove(VerificationCode verificationCode);
}

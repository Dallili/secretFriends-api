package org.dallili.secretfriends.email.repository;

import org.dallili.secretfriends.email.dto.VerificationCode;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository{
    private final Map<String, VerificationCode> repository = new ConcurrentHashMap<>();

    @Override
    public VerificationCode save(VerificationCode verificationCode) {
        return repository.put(verificationCode.getCode(), verificationCode);
    }

    @Override
    public Optional<VerificationCode> findByCode(String code) {
        return Optional.ofNullable(repository.get(code));
    }

    @Override
    public void remove(VerificationCode verificationCode) {
        repository.remove(verificationCode.getCode());
    }
}

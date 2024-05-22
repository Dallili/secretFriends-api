package org.dallili.secretfriends.email.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class EmailDTO {

    @Getter
    public static class SendRequest{
        @Email
        private String email;
    }

    @Getter
    public static class VerifyRequest{
        private String code;
    }
}

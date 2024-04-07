package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MemberDTO {
    @Data
    @Builder
    public static class SignUpRequest{

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min=5, message = "비밀번호가 너무 짧습니다. (최소 5글자)")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max=20, message = "닉네임이 너무 깁니다. (최대 20글자)")
        private String nickname;

        @NotBlank(message = "생년월일을 입력해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;

        @Email
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "성별을 입력해주세요.")
        @Pattern(regexp = "[FM]", message = "성별 값은 F와 M 중 하나여야 한다.")
        private String gender;
    }
}

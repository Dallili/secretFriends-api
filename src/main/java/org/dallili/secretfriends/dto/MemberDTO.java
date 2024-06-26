package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.dallili.secretfriends.domain.MemberRole;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

public class MemberDTO {
    @Data
    @Builder
    public static class SignUpRequest{

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).{8,24}$" , message = "영어 대/소문자, 숫자, 특수문자(!@#$%^*) 를 혼합한 8자 이상 25자 미만의 비밀번호만 허용됩니다.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$" , message = "특수문자를 포함하지 않은 2-20자의 문자만 허용됩니다.")
        private String nickname;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;

        @Email
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "성별을 입력해주세요.")
        @Pattern(regexp = "[FM]", message = "성별 값은 F와 M 중 하나여야 한다.")
        private String gender;
    }

    @Data
    public static class DetailsResponse{
        private String nickname;
        private String gender;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;
        private boolean useFiltering;
        //@Range(min = 0, max = 3)
        //private int diaryNum;
    }

    @Data
    @Builder
    public static class LoginRequest{
        @Email
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Data
    public static class MemberInfo{
        private Long memberID;
        private String email;
        private MemberRole role;
    }

    @Data
    public static class PasswordRequest{
        @NotBlank(message = "'현재 비밀번호' 항목을 입력해주세요.")
        private String oldPassword;
        @NotBlank(message = "'새 비밀번호' 항목을 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*])(?=.*[0-9]).{8,24}$" , message = "영어 대/소문자, 숫자, 특수문자(!@#$%^*) 를 혼합한 8자 이상 25자 미만의 비밀번호만 허용됩니다.")
        private String newPassword;
        @NotBlank(message = "'새 비밀번호 확인' 항목을 입력해주세요.")
        private String confirmPassword;
    }

    @Data
    public static class FilteringUpdateRequest{
        @NotNull
        private boolean useFiltering;
    }
  
    @Data
    public static class ModifyRequest{
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$" , message = "특수문자를 포함하지 않은 2-20자의 문자만 허용됩니다.")
        private String nickname;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;
    }
}

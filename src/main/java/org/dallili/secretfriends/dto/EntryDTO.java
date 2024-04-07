package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryDTO {

    private Long entryID;
    @NotBlank
    private Long diaryID;
    @NotBlank
    private Long writer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @Size(min = 1, message = "일기 내용은 1자 이상 작성해야 한다.")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendAt;
    @Pattern(regexp = "[YN]", message = "state는 Y 혹은 N 값 중 하나여야 한다.")
    private String state;

    @Data
    @Builder
    public static class ModifyRequest{
        private Long entryID;
        @Size(min = 1, message = "일기 내용은 1자 이상 작성해야 한다.")
        private String content;
    }

    @Data
    public static class UnsentEntryResponse{
        private Long entryID;
        @NotBlank
        private Long writer;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date;
        @Size(min = 1, message = "일기 내용은 1자 이상 작성해야 한다.")
        private String content;
    }

    @Data
    public static class SentEntryResponse{
        private Long entryID;
        @NotBlank
        private Long writer;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime sendAt;
        @Size(min = 1, message = "일기 내용은 1자 이상 작성해야 한다.")
        private String content;
    }

}

package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.dallili.secretfriends.domain.Diary;
import org.dallili.secretfriends.domain.Entry;

import java.time.LocalDateTime;

public class EntryDTO {

    @Data
    @Builder
    public static class CreateRequest{
        @NotNull
        private Long diaryID;
        @Size(min = 1, message = "일기 내용은 1자 이상 작성해야 한다.")
        private String content;
        @JsonIgnore
        private Long writerID;

        public Entry toEntity(Diary diary){
            return Entry.builder()
                    .diary(diary)
                    .writerID(this.writerID)
                    .content(this.content)
                    .build();
        }
    }

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

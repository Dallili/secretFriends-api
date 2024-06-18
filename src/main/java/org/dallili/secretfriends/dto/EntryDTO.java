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
import org.dallili.secretfriends.domain.Member;

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

        public Entry toEntity(Diary diary, Member member){
            return Entry.builder()
                    .diary(diary)
                    .member(member)
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
    @Builder
    public static class UnsentEntryResponse{
        private Long entryID;
        private String writerName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime updatedAt;
        private String content;
    }

    @Data
    @Builder
    public static class SentEntryResponse{
        private Long entryID;
        private String writerName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime sendAt;
        private String content;

        public void changeContent(String content){ this.content = content; }
    }

}

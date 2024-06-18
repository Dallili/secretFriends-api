package org.dallili.secretfriends.notify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dallili.secretfriends.notify.domain.Notify;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class NotifyDTO {

    @NotNull
    private Long notifyID;
    private NotifyDTO.NotifyType notifyType;
    @NotNull
    private Long receiverID;
    private Long senderID;
    private Long diaryID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    public enum NotifyType{
        NEWDIARY, REPLY, INACTIVATE
    }

    @Data
    @Builder
    public static class notifyResponse{
        @NotNull
        private Long notifyID;
        private NotifyDTO.NotifyType notifyType;
        @NotNull
        private String receiverNickname;
        private String senderNickname;
        private String diaryColor;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt; // get, set


    }



}

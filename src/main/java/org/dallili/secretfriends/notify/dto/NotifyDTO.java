package org.dallili.secretfriends.notify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dallili.secretfriends.notify.domain.Notify;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NotifyDTO {

    @NotNull
    private Long notifyID;

    private Enum<NotifyDTO.NotifyType> notifyType;

    @NotNull
    private Long receiverID; //get, set

    private Long senderID; //get, set

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    public enum NotifyType{
        NEWDIARY, REPLY, INACTIVATE
    }
}

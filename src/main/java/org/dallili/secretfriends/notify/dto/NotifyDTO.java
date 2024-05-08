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

    private Enum<NotifyDTO.NotifyType> notifyType;

    @NotNull
    private Long receiverID; //get, set

    private Long senderID; //get, set

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    public enum NotifyType{
        NEWDIARY, REPLY, INACTIVATE
    }

    //modelmapper 매핑 규칙 정의를 위한 setter
    //일반 코드 작성 시에는 사용 지양
    public void setNotifyType(Enum<NotifyType> notifyType) {
        this.notifyType = notifyType;
    }
}

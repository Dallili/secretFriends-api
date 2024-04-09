package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DiaryDTO {

    @NotNull
    private Long diaryID; //get

    @NotNull
    private Long memberID; //get, set

    private Long partnerID; //get, set

    private boolean state; //get, set

    private Long updatedBy; //get. set

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    private String color; //get

    private UUID code;

    @Data
    @Builder
    public static class knownMatchingDiary{
        private Long memberID;
        private String color;
    }


}

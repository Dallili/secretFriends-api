package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dallili.secretfriends.domain.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DiaryDTO {

    @NotNull
    private Long diaryID; //get

    @NotEmpty
    private String userID; //get, set

    private String partnerID; //get, set

    private boolean state; //get, set

    private String updatedBy; //get. set

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    private String color; //get


}

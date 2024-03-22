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
    private String diaryID; //get

    @NotEmpty
    private User user; //get, set

    private User partner; //get, set

    private boolean isActivated; //get, set

    private String updatedBy; //get. set

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // get, set

    private String color; //get



}

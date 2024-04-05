package org.dallili.secretfriends.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchingHistoryDTO {

    @NotBlank
    private Long historyID;
    @NotBlank
    private String userID;
    @NotBlank
    private String partnerID;
}

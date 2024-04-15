package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchingDTO {

    private Long matchingID;

    private Long memberID;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long firstInterest;

    private Long secondInterest;

    private Long thirdInterest;

    @Data
    @Builder
    public static class newMatching{

        private LocalDateTime createdAt;

        private Long firstInterest;

        private Long secondInterest;

        private Long thirdInterest;
    }
}

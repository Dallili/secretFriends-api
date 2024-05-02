package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

public class NoticeDTO {
    @Data
    public static class CreateRequest{
        @NotBlank
        String title;
        @NotBlank
        String content;
        Boolean pin;
    }
    @Data
    public static class ListResponse{
        Long noticeID;
        String title;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt;
    }
    @Data
    public static class DetailsResponse{
        Long noticeID;
        String title;
        String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt;
    }

}

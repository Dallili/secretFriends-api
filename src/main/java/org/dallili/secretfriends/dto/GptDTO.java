package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GptDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message{
        private String role;
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice{
        private int index;
        private Message message;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @AllArgsConstructor
    @Builder
    public static class Request{
        private String model;
        private List<Message> messages;
        private int temperature;  //0  ~ 2 사이의 값. 값이 높을수록 출력이 더 무작위로 만들어지고, 값이 낮을수록 더 집중적이고 결정적이게 된다.
        private int maxTokens;
        private int topP;
        private int frequencyPenalty; //-2.0 ~ 2.0 사이의 값. 양수 값은 새 토큰에 불이익을 주어 모델이 동일한 줄을 그대로 반복할 가능성을 줄인다.
        private int presencePenalty;  //-2.0 ~ 2.0 사이의 값. 양수 값은 새 토큰에 불이익을 주어 모델이 새로운 주제에 관해 이야기할 가능성을 높인다.
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private List<Choice> choices;
    }

}

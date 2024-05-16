package org.dallili.secretfriends.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.dallili.secretfriends.domain.Entry;
import org.dallili.secretfriends.domain.Report;

import java.time.LocalDate;

public class ReportDTO {

    @Data
    @Builder
    public static class Details{
        private String sentiment;
        private String summary;
        private String color;

        public Report toEntity(Entry entry){
            return Report.builder()
                    .entry(entry)
                    .sentiment(this.sentiment)
                    .summary(this.summary)
                    .color(this.color)
                    .build();
        }
    }

    @Data
    @Builder
    public static class List{
        private int index;
        private Long entryID;
        private String sentiment;
        private String summary;
        private String color;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
    }

}

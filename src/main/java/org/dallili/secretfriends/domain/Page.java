package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "diary")
@Table(name = "page", indexes = {
        @Index(name="idx_page_diary_diaryID", columnList = "diary_diaryID")
})
public class Page {

    @Id
    @Column(name = "pageID", length = 20)
    private String pageID;

    @JoinColumn(name = "diaryID", referencedColumnName = "diaryID", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY) // 여러 개의 페이지가 하나의 다이어리에 속할 수 있음.
    private Diary diary;

    @Column(name = "userID")
    private String userID;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "sendAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime sendAt;

    @Column(name = "state")
    private char state;





}
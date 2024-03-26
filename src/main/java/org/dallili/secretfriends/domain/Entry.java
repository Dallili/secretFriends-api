package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "diary")
@Table(name = "entry", indexes = {
        @Index(name="idx_entry_diary_diaryID", columnList = "diary_diaryID")
})
@EntityListeners(value = {AuditingEntityListener.class})
@DynamicInsert
public class Entry {

    @Id
    @Column(name = "entryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryID;

    @JoinColumn(name = "diaryID", referencedColumnName = "diaryID", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY) // 여러 개의 페이지가 하나의 다이어리에 속할 수 있음.
    private Diary diary;

    @Column(name = "writer")
    private String writer;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime date;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "sendAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime sendAt;

    @Column(name = "state", length = 1)
    @Pattern(regexp = "[YN]", message = "state는 Y 혹은 N 값 중 하나여야 한다.")
    @Builder.Default
    private String state = "N";

    @PrePersist
    public void defaultState(){
        if(this.state == null) {
            this.state = "N";
        }
    }

}
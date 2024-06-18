package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.dallili.secretfriends.dto.EntryDTO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"diary","member"})
@Table(name = "entry", indexes = {
        @Index(name="idx_entry_diary_diaryID", columnList = "diary_id")
})
@EntityListeners(value = {AuditingEntityListener.class})
@DynamicInsert
public class Entry {

    @Id
    @Column(name = "entry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryID;

    @JoinColumn(name = "diary_id", referencedColumnName = "diary_id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY) // 여러 개의 페이지가 하나의 다이어리에 속할 수 있음.
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Diary diary;

    @JoinColumn(name = "writer_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "sendAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime sendAt;

    @Column(name = "state", length = 1)
    @Pattern(regexp = "[YN]", message = "state는 Y 혹은 N 값 중 하나여야 한다.")
    @Builder.Default
    private String state = "N";

    public void changeContent(String content){
        this.content = content;
    }

    @PrePersist
    public void defaultState(){
        if(this.state == null) {
            this.state = "N";
        }
    }

    @PreUpdate
    public void preventUpdate(){
        if(this.state.equals("Y")){
            throw new IllegalStateException("전달된 일기는 내용 수정 불가");
        }
    }

    public EntryDTO.UnsentEntryResponse toUnsentDto(){
        return EntryDTO.UnsentEntryResponse.builder()
                .entryID(this.getEntryID())
                .writerName(this.getMember().getNickname())
                .updatedAt(this.getUpdatedAt())
                .content(this.getContent())
                .build();
    }
    public EntryDTO.SentEntryResponse toSentDto(){
        return EntryDTO.SentEntryResponse.builder()
                .entryID(this.getEntryID())
                .writerName(this.getMember().getNickname())
                .sendAt(this.getSendAt())
                .content(this.getContent())
                .build();
    }

}
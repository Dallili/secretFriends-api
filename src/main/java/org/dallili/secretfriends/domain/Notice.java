package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "notice")
@EntityListeners(value = {AuditingEntityListener.class})
public class Notice {
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noticeID;

    @Column(length = 30)
    String title;

    @Column(length = 1000)
    String content;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    @CreatedDate
    LocalDateTime createdAt;

    @Column(columnDefinition = "TINYINT")
    Boolean pin;
}

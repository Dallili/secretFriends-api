package org.dallili.secretfriends.notify.domain;

import jakarta.persistence.*;
import lombok.*;
import org.dallili.secretfriends.domain.Member;
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
@ToString(exclude = {"receiver", "sender"})
@EntityListeners(value = {AuditingEntityListener.class})
@Table (name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyID;

    @Enumerated(EnumType.STRING)
    private NotifyType notifyType;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne (fetch = FetchType.LAZY) //여러 개의 알림이 하나의 user에 속할 수 있음
    @JoinColumn(name = "receiver_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver; // 알림 받는 사람

    @ManyToOne (fetch = FetchType.LAZY) //여러 개의 알림이 하나의 user에 속할 수 있음
    @JoinColumn(name = "sender_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member sender; // 일기 비활성화 시킨 사람, 답장을 보낸 사람.

    public enum NotifyType{
        NEWDIARY, REPLY, INACTIVATE
    }
}


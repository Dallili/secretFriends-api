package org.dallili.secretfriends.notify.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.dallili.secretfriends.domain.Member;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotifyType notifyType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver; // 알림 받는 사람

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member sender; // 일기 비활성화 시킨 사람, 답장을 보낸 사람.


    /*
    @Builder
    public Notify(Member receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }

    public Notify() {

    }*/


    public enum NotifyType{
        NEWDIARY, REPLY, INACTIVATE
    }
}
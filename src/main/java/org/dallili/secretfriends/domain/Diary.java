package org.dallili.secretfriends.domain;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//엔티티 객체를 위한 엔티티 클래스는 반드시 @Entity를 적용해야하고 @Id가 필요하다
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user","partner"})
@Table (name = "diary")
public class Diary{

    @Id
    @Column(name = "diaryID", length = 20)
    private String diaryID;

    @ManyToOne(fetch = FetchType.LAZY) //여러 개의 diary가 하나의 user에 속할 수 있음
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) //여러 개의 diary가 하나의 user에 속할 수 있음
    @JoinColumn(name = "partnerID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User partner;

    @Column(name = "isActivated",columnDefinition = "TINYINT")
    private boolean isActivated;

    @Column(name = "updatedBy", length = 20)
    private String updatedBy;

    @Column(name = "updatedAt",columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "color", length = 7)
    private String color;





}
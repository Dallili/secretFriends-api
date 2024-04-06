package org.dallili.secretfriends.domain;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//엔티티 객체를 위한 엔티티 클래스는 반드시 @Entity를 적용해야하고 @Id가 필요하다
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member","partner"})
@EntityListeners(value = {AuditingEntityListener.class})
@Table (name = "diary")
public class Diary{

    @Id
    @Column(name = "diaryID", length = 20)
    private String diaryID;

    @ManyToOne(fetch = FetchType.LAZY) //여러 개의 diary가 하나의 user에 속할 수 있음
    @JoinColumn(name = "memberID", referencedColumnName = "memberID", insertable = true, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) //여러 개의 diary가 하나의 user에 속할 수 있음
    @JoinColumn(name = "partnerID", referencedColumnName = "memberID", insertable = true, updatable = true)
    private Member partner;

    @Column(name = "state",columnDefinition = "TINYINT")
    private boolean state;

    @Column(name = "updatedBy", length = 20)
    private Long updatedBy;

    @Column(name = "updatedAt",columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "color", length = 7)
    private String color;

    @PrePersist
    public void prePersist() {
        this.state = true;
    }

    public void updateDiary(Long updatedBy, LocalDateTime updatedAt){
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public void decidePartner(Member partner){
        this.partner = partner;
    }

    public void changeState(Boolean state){
        this.state = state;
    }


    //modelmapper 매핑 규칙 정의를 위한 setter
    //일반 코드 작성 시에는 사용 지양
    public void setMember(Member member){
        this.member = member;
    }

    public void setPartner(Member partner){
        this.partner = partner;
    }




}
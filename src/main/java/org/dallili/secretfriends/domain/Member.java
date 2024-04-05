package org.dallili.secretfriends.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@ToString
public class Member {

    @Id
    @Column(name = "memberID", length = 20)
    private String memberID;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "birthday", columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "gender")
    private char gender;

    @Column(name = "useFiltering",columnDefinition = "TINYINT")
    private boolean useFiltering;

    //modelmapper 매핑 규칙 정의를 위한 setter
    //일반 코드 작성 시에는 사용 지양

    public void setMemberID(String memberID){
        this.memberID = memberID;
    }

}
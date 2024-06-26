package org.dallili.secretfriends.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", length = 20)
    private Long memberID;

    @Column(name = "email", unique = true, nullable = false,length = 255)
    private String email;

    @Column(name = "password",nullable = false, length = 255)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$" , message = "특수문자를 포함하지 않은 2-20자의 문자만 허용됩니다.")
    private String nickname;

    @Column(name = "birthday", columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(name = "gender", length = 1)
    @Pattern(regexp = "[FM]", message = "성별 값은 F와 M 중 하나여야 한다.")
    private String gender;

    @Column(name = "useFiltering",columnDefinition = "TINYINT")
    private boolean useFiltering;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    //modelmapper 매핑 규칙 정의를 위한 setter
    //일반 코드 작성 시에는 사용 지양

    public void setMemberID(Long memberID){
        this.memberID = memberID;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeFiltering(boolean useFiltering){
        this.useFiltering = useFiltering;
    }

    public void addRole(MemberRole memberRole){
        this.role = memberRole;
    }

    //회원 정보 수정 (별명, 생일)
    public void modifyProfile(String nickname, LocalDate birthday){
        this.nickname = nickname;
        this.birthday = birthday;
    }

}
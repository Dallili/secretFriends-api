package org.dallili.secretfriends.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", length = 20)
    private String userID;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phoneNum", length = 20)
    private String phoneNum;

    @Column(name = "gender")
    private char gender;

    @Column(name = "useFiltering")
    private boolean useFiltering;

}
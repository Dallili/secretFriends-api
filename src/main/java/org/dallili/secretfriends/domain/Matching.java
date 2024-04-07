package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "matching")
public class Matching {

    @Id
    @Column(name = "matchingID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingID;

    @JoinColumn(name = "memberID", referencedColumnName = "memberID", insertable = true, updatable = true)
    private String memberID;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @JoinColumn(name = "firstInterest", referencedColumnName = "interestID", insertable = true, updatable = true)
    private Long firstInterest;

    @JoinColumn(name = "secondInterest", referencedColumnName = "interestID", insertable = true, updatable = true)
    private Long secondInterest;

    @JoinColumn(name = "thirdInterest", referencedColumnName = "interestID", insertable = true, updatable = true)
    private Long thirdInterest;



}

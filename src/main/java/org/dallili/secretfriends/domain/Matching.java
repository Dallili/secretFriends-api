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
    @Column(name = "matching_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingID;

    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = true)
    private Long memberID;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @JoinColumn(name = "firstInterest", referencedColumnName = "interest_id", insertable = true, updatable = true)
    private Long firstInterest;

    @JoinColumn(name = "secondInterest", referencedColumnName = "interest_id", insertable = true, updatable = true)
    private Long secondInterest;

    @JoinColumn(name = "thirdInterest", referencedColumnName = "interest_id", insertable = true, updatable = true)
    private Long thirdInterest;



}

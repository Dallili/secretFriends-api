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
@Table(name = "matchingHistory")
public class MatchingHistory {

    @Id
    @Column(name = "historyID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyID;

    @JoinColumn(name = "memberID", referencedColumnName = "memberID", insertable = true, updatable = true)
    private String memberID;

    @JoinColumn(name = "partnerID", referencedColumnName = "memberID", insertable = true, updatable = true)
    private String partnerID;

}

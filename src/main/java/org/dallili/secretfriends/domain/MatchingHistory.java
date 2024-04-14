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
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyID;

    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = true)
    private String memberID;

    @JoinColumn(name = "partner_id", referencedColumnName = "member_id", insertable = true, updatable = true)
    private String partnerID;

}

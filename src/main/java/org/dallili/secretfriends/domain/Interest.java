package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "interest")
public class Interest {

    @Id
    @Column(name = "interest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestID;

    @Column(name = "type", length = 20)
    private String type;

}

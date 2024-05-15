package org.dallili.secretfriends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"entry"})
@Table(name = "report", indexes = {
        @Index(name="idx_report_entry_entryID", columnList = "entry_id")
})
@EntityListeners(value = {AuditingEntityListener.class})
@DynamicInsert
public class Report {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @JoinColumn(name = "entry_id", referencedColumnName = "entry_id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entry entry;

    private String sentiment;

    @Column(length = 1000)
    private String summary;

    @Column(name = "color", length = 7)
    private String color;   //hex code


}

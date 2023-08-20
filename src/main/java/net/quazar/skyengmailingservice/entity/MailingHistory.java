package net.quazar.skyengmailingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "mailing_history",
    indexes = {
        @Index(name = "mailing_id_index", columnList = "mailing_id")
    })
public class MailingHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mailing_id")
    private Mailing mailing;

    @ManyToOne
    @JoinColumn(name = "postal_office_id")
    private PostalOffice postalOffice;

    @Column(nullable = false)
    private String operation;
}

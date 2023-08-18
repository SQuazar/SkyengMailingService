package net.quazar.skyengmailingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "mailing_history",
    indexes = {
        @Index(name = "mailing_id_index", columnList = "mailing_id")
    })
public class MailingHistory {
    @Id
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mailing_id")
    private Mailing mailing;

    @ManyToOne(optional = false)
    @JoinColumn(name = "postal_office_id")
    private PostalOffice postalOffice;

    @Column(nullable = false)
    private String operation;
}

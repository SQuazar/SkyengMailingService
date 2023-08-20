package net.quazar.skyengmailingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "mailing_status")
public class MailingStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mailing_id")
    private Mailing mailing;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        REGISTERED("Зарегистрировано"),
        ROUTING("В пути"),
        RECEIVED("Получено");

        private final String localized;

        Status(String localized) {
            this.localized = localized;
        }

        public String getLocalized() {
            return localized;
        }
    }
}

package net.quazar.skyengmailingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "mailing_status")
public class MailingStatus {
    @ManyToOne
    @Column(name = "mailing_id")
    @Id
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

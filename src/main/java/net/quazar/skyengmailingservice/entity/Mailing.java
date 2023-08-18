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
@Table(name = "mailing",
        indexes = {
                @Index(name = "receiver_name_index", columnList = "receiver_name")
        })
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String receiverIndex;

    @Column(nullable = false)
    private String receiverAddress;

    @Column(nullable = false)
    private String receiverName;

    public enum Type {
        MESSAGE("Письмо"),
        PACKAGE("Посылка"),
        PARCEL("Бандероль"),
        POSTCARD("Открытка");

        private final String localized;

        Type(String localized) {
            this.localized = localized;
        }

        public String getLocalized() {
            return localized;
        }
    }
}

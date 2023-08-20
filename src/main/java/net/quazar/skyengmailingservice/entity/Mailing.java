package net.quazar.skyengmailingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @Column(name = "receiver_index", nullable = false)
    private String receiverIndex;

    @Column(name = "receiver_address", nullable = false)
    private String receiverAddress;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
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

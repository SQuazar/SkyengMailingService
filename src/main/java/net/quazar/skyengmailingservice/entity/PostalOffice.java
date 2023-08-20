package net.quazar.skyengmailingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "postla_office",
    indexes = {
        @Index(name = "name_index", columnList = "name", unique = true)
    })
public class PostalOffice {
    @Id
    private String index;
    private String name;
    private String address;
}

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

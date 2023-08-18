package net.quazar.skyengmailingservice.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalOfficeDto {
    private String index;
    private String name;
    private String address;
}

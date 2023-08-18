package net.quazar.skyengmailingservice.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailingDto {
    private int id;
    private String type;
    private String receiverIndex;
    private String receiverAddress;
    private String receiverName;
    private String status;
}

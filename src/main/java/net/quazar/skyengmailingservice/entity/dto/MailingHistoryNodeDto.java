package net.quazar.skyengmailingservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MailingHistoryNodeDto {
    private int id;
    private LocalDateTime date;
    @JsonProperty("mailing_id")
    private int mailingId;
    @JsonProperty("postal_office_id")
    private int postalOfficeId;
    private String operation;
}

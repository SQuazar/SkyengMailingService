package net.quazar.skyengmailingservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.quazar.skyengmailingservice.entity.Mailing;

@Data
public final class RegisterMailingRequest {
    @JsonProperty("mailing_type")
    @NotNull(message = "mailing_type cannot be null or empty")
    @Enumerated
    private final Mailing.Type mailingType;

    @JsonProperty("receiver_index")
    @NotBlank(message = "receiver_index cannot be null or empty")
    @Pattern(regexp = "[0-9]{6}", message = "Invalid receiver_index format")
    private final String receiverIndex;

    @JsonProperty("receiver_address")
    @NotBlank(message = "receiver_address cannot be null or empty")
    private final String receiverAddress;

    @JsonProperty("receiver_name")
    @NotBlank(message = "receiver_name cannot be null or empty")
    private final String receiverName;
}
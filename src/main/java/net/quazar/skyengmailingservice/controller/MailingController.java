package net.quazar.skyengmailingservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;
import net.quazar.skyengmailingservice.service.MailingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/mailing")
public class MailingController {
    private final MailingService mailingService;

    @GetMapping("/{mailingId}")
    public MailingDto getMailingById(@PathVariable int mailingId) {
        return mailingService.getMailingById(mailingId);
    }

    @GetMapping("/{mailingId}/history")
    public List<MailingHistoryNodeDto> getMailingHistory(@PathVariable int mailingId) {
        return mailingService.getMailingHistory(mailingId);
    }

    @PostMapping
    public MailingDto registerNewMailing(@RequestBody @Valid RegisterMailingRequest request) {
        return mailingService.registerNewMailing(request.mailingType, request.receiverIndex, request.receiverAddress, request.receiverName);
    }

    @PostMapping("/{mailingId}/delivered")
    public MailingDto setDelivered(@PathVariable int mailingId) {
        mailingService.changeMailingStatus(mailingId, MailingStatus.Status.RECEIVED);
        return mailingService.getMailingById(mailingId);
    }

    @Data
    @Builder
    static final class RegisterMailingRequest {
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
}

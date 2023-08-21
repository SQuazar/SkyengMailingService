package net.quazar.skyengmailingservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.quazar.skyengmailingservice.controller.model.RegisterMailingRequest;
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
        return mailingService.registerNewMailing(request.getMailingType(), request.getReceiverIndex(), request.getReceiverAddress(), request.getReceiverName());
    }

    @PostMapping("/{mailingId}/delivered")
    public MailingDto setDelivered(@PathVariable int mailingId) {
        mailingService.changeMailingStatus(mailingId, MailingStatus.Status.RECEIVED);
        return mailingService.getMailingById(mailingId);
    }
}

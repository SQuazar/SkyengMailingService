package net.quazar.skyengmailingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.quazar.skyengmailingservice.controller.model.RegisterMailingRequest;
import net.quazar.skyengmailingservice.controller.model.ServerError;
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

    @Operation(
            description = "Получает почтовое отправление по его ID",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ServerError.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{mailingId}")
    public MailingDto getMailingById(@PathVariable int mailingId) {
        return mailingService.getMailingById(mailingId);
    }

    @Operation(
            description = "Получает историю почтового отправления по его ID",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ServerError.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{mailingId}/history")
    public List<MailingHistoryNodeDto> getMailingHistory(@PathVariable int mailingId) {
        return mailingService.getMailingHistory(mailingId);
    }

    @Operation(
            description = "Регистрирует новое почтовое отправление"
    )
    @PostMapping
    public MailingDto registerNewMailing(@RequestBody @Valid RegisterMailingRequest request) {
        return mailingService.registerNewMailing(request.getMailingType(), request.getReceiverIndex(), request.getReceiverAddress(), request.getReceiverName());
    }

    @Operation(
            description = "Устанавливает статус почтового отправления как \"Получено\"",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ServerError.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/{mailingId}/delivered")
    public MailingDto setDelivered(@PathVariable int mailingId) {
        mailingService.changeMailingStatus(mailingId, MailingStatus.Status.RECEIVED);
        return mailingService.getMailingById(mailingId);
    }
}

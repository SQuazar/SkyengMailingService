package net.quazar.skyengmailingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.quazar.skyengmailingservice.controller.model.CreatePostalOfficeRequest;
import net.quazar.skyengmailingservice.controller.model.ServerError;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;
import net.quazar.skyengmailingservice.service.PostalOfficeService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/postal")
public class PostalOfficeController {
    private final PostalOfficeService postalOfficeService;

    @Operation(
            description = "Получает почтовое отделение по его индексу",
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
    @GetMapping("/{index}")
    public PostalOfficeDto getByIndex(@PathVariable String index) {
        return postalOfficeService.findByIndex(index);
    }

    @Operation(
            description = "Получает список всех почтовых отделений"
    )
    @GetMapping
    public Set<PostalOfficeDto> getAll() {
        return postalOfficeService.findAll();
    }

    @Operation(
            description = "Создаёт новое почтовое отделение"
    )
    @PostMapping
    public PostalOfficeDto createPostalOffice(@Valid @RequestBody CreatePostalOfficeRequest request) {
        return postalOfficeService.createPostalOffice(request.getIndex(), request.getAddress(), request.getName());
    }

    @Operation(
            description = "Регистрирует почтовое отправление в указанном почтовом отделении",
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
    @PostMapping("/{index}/incoming/{mailingId}")
    public void registerIncomingMailing(@PathVariable String index, @PathVariable int mailingId) {
        postalOfficeService.registerIncomingMailing(index, mailingId);
    }

    @Operation(
            description = "Регистрирует отправку почтового отправления из почтового отделения",
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
    @PostMapping("/{index}/outgoing/{mailingId}")
    public void registerOutgoingMailing(@PathVariable String index, @PathVariable int mailingId) {
        postalOfficeService.registerOutgoingMailing(index, mailingId);
    }
}

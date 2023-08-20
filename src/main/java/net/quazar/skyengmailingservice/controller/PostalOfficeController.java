package net.quazar.skyengmailingservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;
import net.quazar.skyengmailingservice.service.PostalOfficeService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/postal")
public class PostalOfficeController {
    private final PostalOfficeService postalOfficeService;

    @GetMapping("/{index}")
    public PostalOfficeDto getByIndex(@PathVariable String index) {
        return postalOfficeService.findByIndex(index);
    }

    @GetMapping
    public Set<PostalOfficeDto> getAll() {
        return postalOfficeService.findAll();
    }

    @PostMapping
    public PostalOfficeDto createPostalOffice(@Valid @RequestBody CreatePostalOfficeRequest request) {
        return postalOfficeService.createPostalOffice(request.getIndex(), request.getAddress(), request.getName());
    }

    @PostMapping("/{index}/incoming/{mailingId}")
    public void registerIncomingMailing(@PathVariable String index, @PathVariable int mailingId) {
        postalOfficeService.registerIncomingMailing(index, mailingId);
    }

    @PostMapping("/{index}/outgoing/{mailingId}")
    public void registerOutgoingMailing(@PathVariable String index, @PathVariable int mailingId) {
        postalOfficeService.registerOutgoingMailing(index, mailingId);
    }

    @Data
    static final class CreatePostalOfficeRequest {
        @NotBlank(message = "index cannot be null or empty")
        @Pattern(regexp = "[0-9]{6}", message = "Invalid index format")
        private final String index;
        private final @NotBlank(message = "address cannot be null or empty") String address;
        private final @NotBlank(message = "name cannot be null or empty") String name;
    }
}

package net.quazar.skyengmailingservice.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public final class CreatePostalOfficeRequest {
    @NotBlank(message = "index cannot be null or empty")
    @Pattern(regexp = "[0-9]{6}", message = "Invalid index format")
    private final String index;
    private final @NotBlank(message = "address cannot be null or empty") String address;
    private final @NotBlank(message = "name cannot be null or empty") String name;
}
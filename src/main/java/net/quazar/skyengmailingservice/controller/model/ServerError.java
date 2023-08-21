package net.quazar.skyengmailingservice.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ServerError {
    private final int code;
    private final String message;
}

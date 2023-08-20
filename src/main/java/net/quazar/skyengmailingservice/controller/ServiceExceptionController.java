package net.quazar.skyengmailingservice.controller;


import lombok.Builder;
import lombok.Data;
import net.quazar.skyengmailingservice.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServiceExceptionController {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServerError> handleServiceException(ServiceException e) {
        ResponseStatus responseStatus;
        int code = 500;
        if ((responseStatus = e.getClass().getAnnotation(ResponseStatus.class)) != null)
            code = responseStatus.code().value();
        return ResponseEntity.status(code)
                .body(
                        ServerError.builder()
                                .code(code)
                                .message(e.getMessage())
                                .build()
                );
    }

    @Data
    @Builder
    public static final class ServerError {
        private final int code;
        private final String message;
    }
}

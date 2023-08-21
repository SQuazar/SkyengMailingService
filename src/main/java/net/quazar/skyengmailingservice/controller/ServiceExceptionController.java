package net.quazar.skyengmailingservice.controller;


import net.quazar.skyengmailingservice.controller.model.ServerError;
import net.quazar.skyengmailingservice.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionController {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServerError> handleServiceException(ServiceException e) {
        ResponseStatus responseStatus;
        int code = 500;
        if ((responseStatus = e.getClass().getAnnotation(ResponseStatus.class)) != null)
            code = responseStatus.value().value();
        return ResponseEntity.status(code)
                .body(ServerError.builder()
                                .code(code)
                                .message(e.getMessage())
                                .build()
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerError> handleNotValidException(MethodArgumentNotValidException e) {
        String message = "Invalid request body:" + ' ' +
                e.getBindingResult().getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(", "));
        return ResponseEntity.status(400)
                .body(ServerError.builder()
                        .code(400)
                        .message(message)
                        .build()
                );
    }
}

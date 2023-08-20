package net.quazar.skyengmailingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostalOfficeAlreadyExistsException extends ServiceException {
    public PostalOfficeAlreadyExistsException() {
    }

    public PostalOfficeAlreadyExistsException(String message) {
        super(message);
    }

    public PostalOfficeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

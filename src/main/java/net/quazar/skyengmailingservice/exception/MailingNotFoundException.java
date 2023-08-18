package net.quazar.skyengmailingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MailingNotFoundException extends ServiceException {
    public MailingNotFoundException() {
    }

    public MailingNotFoundException(String message) {
        super(message);
    }

    public MailingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

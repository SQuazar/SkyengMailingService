package net.quazar.skyengmailingservice.exception;

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

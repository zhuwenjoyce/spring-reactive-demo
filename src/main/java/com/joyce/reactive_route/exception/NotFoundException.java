package com.joyce.reactive_route.exception;

public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(Long id) {
        super(String.format("Entity %s Not found", id));
    }

    public NotFoundException(Long id, Throwable cause) {
        super(String.format("Entity %s Not found", id), cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(Long id, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format("Entity %s Not found", id), cause, enableSuppression, writableStackTrace);
    }
}

package com.joyce.reactive_jasync_mysql.exception;

public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(String id) {
        super(String.format("Entity %s Not found", id));
    }

    public NotFoundException(String id, Throwable cause) {
        super(String.format("Entity %s Not found", id), cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String id, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format("Entity %s Not found", id), cause, enableSuppression, writableStackTrace);
    }
}

package com.syedhisham41.cron_validator.Exceptions;

public class DangerousCronException extends RuntimeException {

    public DangerousCronException(String message) {
        super(message);
    }

    public DangerousCronException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.craftsoft.test.cdr.core.exception;

/** Exception wrapper for all handled exceptions
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
public class CdrApiException extends RuntimeException {

    public CdrApiException(String message) {
        super(message);
    }
}

package org.online.store.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;          // e.g. VALIDATION_ERROR, NOT_FOUND, CONFLICT, FORBIDDEN, UNAUTHORIZED, SERVER_ERROR
    private String messageKey;     // i18n key, e.g. errors.validation
    private Map<String, String> fieldErrors;
}

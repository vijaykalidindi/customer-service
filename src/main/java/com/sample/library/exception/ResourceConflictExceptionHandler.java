package com.sample.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
@Slf4j
public class ResourceConflictExceptionHandler {

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(CONFLICT)
    @ResponseBody
    public ApiError handleResourceConflictException(final ResourceConflictException exception) {
        final ApiError apiError = ApiError.builder()
                .status(CONFLICT.value())
                .origin("")
                .errorMsg(exception.getMessage()).build();
        log.error(exception.getMessage());
        return apiError;
    }
}
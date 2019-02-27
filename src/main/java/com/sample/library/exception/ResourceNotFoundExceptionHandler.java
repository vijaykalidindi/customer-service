package com.sample.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
@Slf4j
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ApiError handleResourceNotFoundException(final ResourceNotFoundException exception) {
        final ApiError apiError = ApiError.builder()
                .status(NOT_FOUND.value())
                .origin("")
                .errorMsg(exception.getMessage()).build();
        log.error(exception.getMessage());
        return apiError;
    }
}

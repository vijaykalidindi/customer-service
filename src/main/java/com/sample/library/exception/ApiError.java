package com.sample.library.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private int status;
    private String errorMsg;
    private String origin;
}

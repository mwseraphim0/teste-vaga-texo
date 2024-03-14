package com.br.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}

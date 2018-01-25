package com.lance.demo.gate.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class ErrorRsp {
    private int status;
    private String message;
    private String description;
    private String correlationId;
}

package com.codeEditor.server.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private String message;

    private Boolean success;

    private Object data;
}

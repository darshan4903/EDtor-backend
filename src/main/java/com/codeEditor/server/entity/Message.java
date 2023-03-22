package com.codeEditor.server.entity;

import lombok.Data;

@Data
public class Message {
    private String sendToTopic;

    private String message;

    private String language;
}

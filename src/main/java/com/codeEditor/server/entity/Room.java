package com.codeEditor.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;

    private String code;
    private String language;
}

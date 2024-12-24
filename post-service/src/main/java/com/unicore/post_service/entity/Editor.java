package com.unicore.post_service.entity;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Editor {
    private String name;
    private String email;
    private Instant date;
}

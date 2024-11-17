package com.unicore.classevent_service.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOption {
    private String id;
    private String name;
    private int limit;

    // group indices or student codes
    private List<String> selectors;
}

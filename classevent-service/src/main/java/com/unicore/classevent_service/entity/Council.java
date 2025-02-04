package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Council {
    @Id
    private String id;
    private String code;
    private List<String> teacherCodes;
    private String staffCode;
    private LocalDateTime reportTime;
    private String location;
}

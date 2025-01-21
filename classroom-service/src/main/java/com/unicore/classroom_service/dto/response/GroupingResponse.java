package com.unicore.classroom_service.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupingResponse {
    private String id;
    
    @JsonProperty("start_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endRegisterDate;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;

    @Builder.Default
    private List<Group> groups = new ArrayList<>();
}
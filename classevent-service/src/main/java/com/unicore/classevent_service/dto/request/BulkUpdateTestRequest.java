package com.unicore.classevent_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkUpdateTestRequest {
    @JsonProperty("class_code")
    private String classCode;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonProperty("day_of_week")
    private Integer dayOfWeek;
    private String session;
    private String room;
    private Integer semester;
    private Integer year;

    public String genKey() {
        return classCode + semester + year;
    }

    public String genDescription() {
        // Ignore this warning
        return String.format("- Ngày thi: %s.\n-Thứ: %s.\n- Ca thi: %s.\n- Phòng: %s.", date, dayOfWeek, session, room);
    }    
}

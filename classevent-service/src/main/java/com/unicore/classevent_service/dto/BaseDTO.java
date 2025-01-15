package com.unicore.classevent_service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseDTO {
    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date createdDate;
    @JsonProperty("modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date modifiedDate;
    @JsonProperty("created_by")
    protected String createdBy;
    @JsonProperty("modified_by")
    protected String modifiedBy;
}

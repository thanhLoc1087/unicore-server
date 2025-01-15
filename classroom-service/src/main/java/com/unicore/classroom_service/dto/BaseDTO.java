package com.unicore.classroom_service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseDTO {
    protected String id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date modifiedDate;
    protected String createdBy;
    protected String modifiedBy;
}

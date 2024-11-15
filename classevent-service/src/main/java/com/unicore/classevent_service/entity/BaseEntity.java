package com.unicore.classevent_service.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class BaseEntity {
    @Id
    protected String id;
    protected Date createdDate;
    protected Date modifiedDate;
    protected String createdBy;
    protected String modifiedBy;
}

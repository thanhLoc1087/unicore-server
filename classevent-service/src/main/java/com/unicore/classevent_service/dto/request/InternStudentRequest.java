package com.unicore.classevent_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternStudentRequest {
    private String studentCode;
    private String studentName;
    private String studentEmail;
    private String teacherName;
    private String teacherMail;
    private String internCompany;
    private String internPost;
    private String internContent;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startTime;    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endTime;
    private Float companyReview;
    private String note;
}

package com.unicore.classevent_service.dto.response;

import java.util.Date;
import java.util.Optional;

import com.unicore.classevent_service.entity.Report;
import com.unicore.classevent_service.entity.Submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassGradeDetailByType {
    private String title;
    private Date date;
    private Float grade;
    private Float weight;
    private boolean fixed;

    public static StudentClassGradeDetailByType fromEventSubmission(EventSubmissionResponse eventSubmission) {
        Float grade = Optional.ofNullable(eventSubmission.getSubmission())
            .map(Submission::getGrade)
            .orElse(0.0f);
        if (eventSubmission.getEvent() instanceof Report report) {
            grade = report.getGrades().getOrDefault(eventSubmission.getStudentCode(), 0f);
        }
        return new StudentClassGradeDetailByType(
            eventSubmission.getEvent().getName(),
            eventSubmission.getEvent().getCreatedDate(),
            grade, 
            eventSubmission.getEvent().getWeight(),
            eventSubmission.getEvent().isFixedWeight()
        );
    }
}

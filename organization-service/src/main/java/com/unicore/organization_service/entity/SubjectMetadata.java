package com.unicore.organization_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.unicore.organization_service.enums.ExamFormat;
import com.unicore.organization_service.validator.SubjectWeightConstraint;

import lombok.Getter;
import lombok.Setter;

@Table
@Getter
@Setter
@SubjectWeightConstraint
public class SubjectMetadata {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private String subjectId;

    @Column(name = "midterm_format")
    private ExamFormat midtermFormat;

    @Column(name = "practical_format")
    private ExamFormat practicalFormat;

    @Column(name = "final_format")
    private ExamFormat finalFormat;

    @Column(name = "coursework_weight")
    private int courseworkWeight;

    @Column(name = "midterm_weight")
    private int midtermWeight;

    @Column(name = "practical_weight")
    private int practicalWeight;

    @Column(name = "final_weight")
    private int finalWeight;

    private int semester;
    private int year;
}

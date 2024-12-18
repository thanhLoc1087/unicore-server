package com.unicore.organization_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.enums.ExamFormat;
import com.unicore.organization_service.validator.SubjectWeightConstraint;

import lombok.Data;
import lombok.ToString;

@Table
@Data
@SubjectWeightConstraint
@ToString
public class SubjectMetadata {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private String subjectId;

    @Column(name = "midterm_format")
    @JsonProperty("midterm_format")
    private ExamFormat midtermFormat;

    @Column(name = "practical_format")
    @JsonProperty("practical_format")
    private ExamFormat practicalFormat;

    @Column(name = "final_format")
    @JsonProperty("final_format")
    private ExamFormat finalFormat;

    @Column(name = "coursework_weight")
    @JsonProperty("coursework_weight")
    private int courseworkWeight;

    @Column(name = "midterm_weight")
    @JsonProperty("midterm_weight")
    private int midtermWeight;

    @Column(name = "practical_weight")
    @JsonProperty("practical_weight")
    private int practicalWeight;

    @Column(name = "final_weight")
    @JsonProperty("final_weight")
    private int finalWeight;

    @JsonProperty("midterm_time")
    private int midtermTime;
    @JsonProperty("final_time")
    private int finalTime;

    private int semester;
    private int year;
}

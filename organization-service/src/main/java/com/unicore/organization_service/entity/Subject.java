// package com.unicore.organization_service.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

// import com.unicore.organization_service.enums.ExamFormat;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Data
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class Subject {
//     private String id;

//     @ManyToOne
//     @JoinColumn(name = "org_id")
//     private Organization organization;

//     private String name;
//     private String code;

//     private ExamFormat midtermFormat;
//     private ExamFormat practicalFormat;
//     private ExamFormat finalFormat;

//     private int courseworkWeight;
//     private int midtermWeight;
//     private int practicalWeight;
//     private int finalWeight;

//     private String description;
// }

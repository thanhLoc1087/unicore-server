package com.unicore.grade_service.dto.request;

import com.unicore.grade_service.enums.Ranking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFormUpdateScoreRequest {
    
    private Float complexity1;
    private Float practicality1;
    private Float research1;
    private Float implementation1;
    private Float learning1;
    private Float report1;
    private Float presentation1;
    private Float qna1;
    private Float planning1;
    private Float problemSolving1;

    private Float complexity2;
    private Float practicality2;
    private Float research2;
    private Float implementation2;
    private Float learning2;
    private Float report2;
    private Float presentation2;
    private Float qna2;
    private Float planning2;
    private Float problemSolving2;

    private int pageCount;
    private int chapterCount;
    private int imageCount;
    private int tableCount;
    private int referenceCount;
    private String formatFeedback;
    private String contentFeedback;
    private String applicationFeedback;
    private String workEthicFeedback;
    private String otherFeedback;
    private Boolean passed;
    private Ranking ranking;

    private boolean tribute;
    private boolean abstractVn;
    private boolean abstractEn;
    private boolean abbrTable;
    private boolean index;
}

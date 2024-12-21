package com.unicore.grade_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GuideReviewForm extends ReviewForm {
    private boolean tribute;
    private boolean abstractVn;
    private boolean abstractEn;
    private boolean abbrTable;
    private boolean index;
}

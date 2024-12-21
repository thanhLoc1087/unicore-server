package com.unicore.grade_service.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.grade_service.enums.FormType;
import com.unicore.grade_service.enums.Ranking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReviewForm {
    @Id
    private String id;

    private String projectId;

    @Indexed(unique = true)
    private String topicId;
    private String topicName;
    private String topicNameEn;

    private GradingMember teacher;

    private ThesisGradeDetails student1;
    private ThesisGradeDetails student2;

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

    private int semester;
    private int year;

    private FormType type;
}

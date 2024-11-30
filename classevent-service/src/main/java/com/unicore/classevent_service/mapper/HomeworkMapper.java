package com.unicore.classevent_service.mapper;

import org.springframework.stereotype.Component;

import com.unicore.classevent_service.dto.request.HomeworkCreationRequest;
import com.unicore.classevent_service.dto.request.HomeworkUpdateRequest;
import com.unicore.classevent_service.dto.response.HomeworkResponse;
import com.unicore.classevent_service.entity.Homework;

@Component
public class HomeworkMapper {

    public Homework toHomework(HomeworkCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Homework.HomeworkBuilder homework = Homework.builder();

        homework.allowGradeReview( request.getAllowGradeReview() );
        homework.attachmentUrl( request.getAttachmentUrl() );
        homework.classId( request.getClassId() );
        homework.closeSubmissionDate( request.getCloseSubmissionDate() );
        homework.description( request.getDescription() );
        homework.endDate( request.getEndDate() );
        homework.graderCode( request.getGraderCode() );
        homework.inGroup( request.getInGroup() );
        homework.name( request.getName() );
        homework.publishDate( request.getPublishDate() );
        homework.remindGradingDate( request.getRemindGradingDate() );
        homework.reviewTimes( request.getReviewTimes() );
        homework.startDate( request.getStartDate() );
        homework.submissionOption( request.getSubmissionOption() );
        homework.weight( request.getWeight() );

        return homework.build();
    }

    public Homework toHomework(Homework homework, HomeworkUpdateRequest request) {
        if (request == null) {
            return homework;
        }

        if (((Boolean) request.isAllowGradeReview()) != null) {
            homework.setAllowGradeReview(request.isAllowGradeReview());
        }
        if (request.getAttachmentUrl() != null) {
            homework.setAttachmentUrl(request.getAttachmentUrl());
        }
        if (request.getCloseSubmissionDate() != null) {
            homework.setCloseSubmissionDate(request.getCloseSubmissionDate());
        }
        if (request.getDescription() != null) {
            homework.setDescription(request.getDescription());
        }
        if (request.getEndDate() != null) {
            homework.setEndDate(request.getEndDate());
        }
        if (request.getGraderCode() != null) {
            homework.setGraderCode(request.getGraderCode());
        }
        if (((Boolean) request.isInGroup()) != null) {
            homework.setInGroup(request.isInGroup());
        }
        if (request.getName() != null) {
            homework.setName(request.getName());
        }
        if (request.getPublishDate() != null) {
            homework.setPublishDate(request.getPublishDate());
        }
        if (((Integer) request.getReviewTimes()) != null) {
            homework.setReviewTimes(request.getReviewTimes());
        }
        if (request.getStartDate() != null) {
            homework.setStartDate(request.getStartDate());
        }
        if (request.getSubmissionOption() != null) {
            homework.setSubmissionOption(request.getSubmissionOption());
        }
        if (request.getWeight() != null) {
            homework.setWeight(request.getWeight());
        }

        return homework;
    }

    public HomeworkResponse toHomeworkResponse(Homework homework) {
        if ( homework == null ) {
            return null;
        }

        HomeworkResponse.HomeworkResponseBuilder homeworkResponse = HomeworkResponse.builder();

        homeworkResponse.allowGradeReview( homework.isAllowGradeReview() );
        homeworkResponse.attachmentUrl( homework.getAttachmentUrl() );
        homeworkResponse.classId( homework.getClassId() );
        homeworkResponse.closeSubmissionDate( homework.getCloseSubmissionDate() );
        homeworkResponse.createdBy( homework.getCreatedBy() );
        if ( homework.getCreatedDate() != null ) {
            homeworkResponse.createdDate( homework.getCreatedDate() );
        }
        homeworkResponse.description( homework.getDescription() );
        homeworkResponse.endDate( homework.getEndDate() );
        homeworkResponse.graderCode( homework.getGraderCode() );
        homeworkResponse.id( homework.getId() );
        homeworkResponse.inGroup( homework.isInGroup() );
        homeworkResponse.modifiedBy( homework.getModifiedBy() );
        if ( homework.getModifiedDate() != null ) {
            homeworkResponse.modifiedDate( homework.getModifiedDate() );
        }
        homeworkResponse.name( homework.getName() );
        homeworkResponse.publishDate( homework.getPublishDate() );
        homeworkResponse.remindGradingDate( homework.getRemindGradingDate() );
        homeworkResponse.reviewTimes( homework.getReviewTimes() );
        homeworkResponse.startDate( homework.getStartDate() );
        homeworkResponse.subclassCode( homework.getSubclassCode() );
        homeworkResponse.submissionOption( homework.getSubmissionOption() );
        homeworkResponse.weight( homework.getWeight() );

        return homeworkResponse.build();
    }
}

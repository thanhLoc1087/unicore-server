package com.unicore.classevent_service.mapper;

import org.springframework.stereotype.Component;

import com.unicore.classevent_service.dto.request.ReportCreationRequest;
import com.unicore.classevent_service.dto.request.ReportUpdateRequest;
import com.unicore.classevent_service.dto.response.ReportResponse;
import com.unicore.classevent_service.entity.Report;

@Component
public class ReportMapper {
    
    public Report toReport(ReportCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Report.ReportBuilder report = Report.builder();

        report.allowGradeReview( request.getAllowGradeReview() );
        report.attachmentUrl( request.getAttachmentUrl() );
        report.projectId( request.getProjectId() );
        report.classId( request.getClassId() );
        report.closeSubmissionDate( request.getCloseSubmissionDate() );
        report.description( request.getDescription() );
        report.date( request.getDate() );
        report.weight( request.getWeight() );
        report.weightType( request.getWeightType() );
        report.inGroup( request.getInGroup() );
        report.name( request.getName() );
        report.room( request.getRoom() );
        report.publishDate( request.getPublishDate() );
        report.remindGradingDate( request.getRemindGradingDate() );
        report.reviewTimes( request.getReviewTimes() );
        report.submissionOption( request.getSubmissionOption() );
        report.weight( request.getWeight() );

        return report.build();
    }
    public Report toReport(Report report, ReportUpdateRequest request) {
        if (request == null) {
            return report;
        }
    
        if (((Boolean) request.isAllowGradeReview())!= null) {
            report.setAllowGradeReview(request.isAllowGradeReview());
        }
        if (request.getAttachmentUrl() != null) {
            report.setAttachmentUrl(request.getAttachmentUrl());
        }
        if (request.getCloseSubmissionDate() != null) {
            report.setCloseSubmissionDate(request.getCloseSubmissionDate());
        }
        if (request.getDescription() != null) {
            report.setDescription(request.getDescription());
        }
        if (request.getDate() != null) {
            report.setDate(request.getDate());
        }
        if (request.getWeight() != null) {
            report.setWeight(request.getWeight());
        }
        if (request.getWeightType() != null) {
            report.setWeightType(request.getWeightType());
        }
        if (((Boolean) request.isInGroup()) != null) {
            report.setInGroup(request.isInGroup());
        }
        if (request.getName() != null) {
            report.setName(request.getName());
        }
        if (request.getRoom() != null) {
            report.setRoom(request.getRoom());
        }
        if (request.getPublishDate() != null) {
            report.setPublishDate(request.getPublishDate());
        }
        if (((Integer) request.getReviewTimes()) != null) {
            report.setReviewTimes(request.getReviewTimes());
        }
        if (request.getSubmissionOption() != null) {
            report.setSubmissionOption(request.getSubmissionOption());
        }
        if (request.getWeight() != null) {
            report.setWeight(request.getWeight());
        }
    
        return report;
    }
    

    public ReportResponse toReportResponse(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportResponse.ReportResponseBuilder reportResponse = ReportResponse.builder();

        reportResponse.projectId( report.getProjectId() );
        reportResponse.allowGradeReview( report.isAllowGradeReview() );
        reportResponse.attachmentUrl( report.getAttachmentUrl() );
        reportResponse.classId( report.getClassId() );
        reportResponse.closeSubmissionDate( report.getCloseSubmissionDate() );
        reportResponse.description( report.getDescription() );
        reportResponse.date( report.getDate() );
        reportResponse.weight( report.getWeight() );
        reportResponse.weightType( report.getWeightType() );
        reportResponse.id( report.getId() );
        reportResponse.inGroup( report.isInGroup() );
        reportResponse.name( report.getName() );
        reportResponse.room( report.getRoom() );
        reportResponse.publishDate( report.getPublishDate() );
        reportResponse.remindGradingDate( report.getRemindGradingDate() );
        reportResponse.reviewTimes( report.getReviewTimes() );
        reportResponse.submissionOption( report.getSubmissionOption() );
        reportResponse.weight( report.getWeight() );

        return reportResponse.build();
    }
}

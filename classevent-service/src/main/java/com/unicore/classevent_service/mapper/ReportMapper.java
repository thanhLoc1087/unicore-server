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
        report.endDate( request.getEndDate() );
        report.weight( request.getWeight() );
        report.weightType( request.getWeightType() );
        report.inGroup( request.getInGroup() );
        report.name( request.getName() );
        report.place( request.getPlace() );
        report.publishDate( request.getPublishDate() );
        report.remindGradingDate( request.getRemindGradingDate() );
        report.reviewTimes( request.getReviewTimes() );
        report.startDate( request.getStartDate() );
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
        if (request.getEndDate() != null) {
            report.setEndDate(request.getEndDate());
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
        if (request.getPlace() != null) {
            report.setPlace(request.getPlace());
        }
        if (request.getPublishDate() != null) {
            report.setPublishDate(request.getPublishDate());
        }
        if (((Integer) request.getReviewTimes()) != null) {
            report.setReviewTimes(request.getReviewTimes());
        }
        if (request.getStartDate() != null) {
            report.setStartDate(request.getStartDate());
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
        reportResponse.endDate( report.getEndDate() );
        reportResponse.weight( report.getWeight() );
        reportResponse.weightType( report.getWeightType() );
        reportResponse.id( report.getId() );
        reportResponse.inGroup( report.isInGroup() );
        reportResponse.name( report.getName() );
        reportResponse.place( report.getPlace() );
        reportResponse.publishDate( report.getPublishDate() );
        reportResponse.remindGradingDate( report.getRemindGradingDate() );
        reportResponse.reviewTimes( report.getReviewTimes() );
        reportResponse.startDate( report.getStartDate() );
        reportResponse.submissionOption( report.getSubmissionOption() );
        reportResponse.weight( report.getWeight() );

        return reportResponse.build();
    }
}

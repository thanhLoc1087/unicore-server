package com.unicore.classroom_service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.unicore.classroom_service.dto.request.StudentGroupingCreationRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingUpdateRequest;
import com.unicore.classroom_service.dto.response.StudentGroupingResponse;
import com.unicore.classroom_service.entity.Group;
import com.unicore.classroom_service.entity.StudentGrouping;

@Component
public class StudentGroupingMapper {
    public StudentGroupingResponse toGroupingResponse(StudentGrouping studentGrouping) {
        if ( studentGrouping == null ) {
            return null;
        }

        StudentGroupingResponse.StudentGroupingResponseBuilder studentGroupingResponse = StudentGroupingResponse.builder();

        studentGroupingResponse.classId( studentGrouping.getClassId() );
        studentGroupingResponse.endRegisterDate( studentGrouping.getEndRegisterDate() );
        List<Group> list = studentGrouping.getGroups();
        if ( list != null ) {
            studentGroupingResponse.groups( new ArrayList<>( list ) );
        }
        studentGroupingResponse.hasLeader( studentGrouping.isHasLeader() );
        studentGroupingResponse.id( studentGrouping.getId() );
        studentGroupingResponse.maxSize( studentGrouping.getMaxSize() );
        studentGroupingResponse.minSize( studentGrouping.getMinSize() );
        studentGroupingResponse.startRegisterDate( studentGrouping.getStartRegisterDate() );
        studentGroupingResponse.subclassCode( studentGrouping.getSubclassCode() );

        return studentGroupingResponse.build();
    }

    public StudentGrouping toGrouping(StudentGroupingCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        StudentGrouping.StudentGroupingBuilder studentGrouping = StudentGrouping.builder();

        studentGrouping.classId( request.getClassId() );
        studentGrouping.endRegisterDate( request.getEndRegisterDate() );
        List<Group> list = request.getGroups();
        if ( list != null ) {
            studentGrouping.groups( new ArrayList<>( list ) );
        }
        studentGrouping.hasLeader( request.isHasLeader() );
        studentGrouping.maxSize( request.getMaxSize() );
        studentGrouping.minSize( request.getMinSize() );
        studentGrouping.startRegisterDate( request.getStartRegisterDate() );
        studentGrouping.subclassCode( request.getSubclassCode() );

        return studentGrouping.build();
    }

    public StudentGrouping toStudentGrouping(StudentGrouping grouping, StudentGroupingUpdateRequest request) {
        if ( request == null ) {
            return grouping;
        }

        if ( grouping.getGroups() != null ) {
            List<Group> list = request.getGroups();
            if ( list != null ) {
                grouping.getGroups().clear();
                grouping.getGroups().addAll( list );
            }
            else {
                grouping.setGroups( null );
            }
        }
        else {
            List<Group> list = request.getGroups();
            if ( list != null ) {
                grouping.setGroups( new ArrayList<>( list ) );
            }
        }

        List<Group> list = request.getGroups();
        // Update groups
        if (list != null) {
            if (grouping.getGroups() != null) {
                grouping.getGroups().clear();
                grouping.getGroups().addAll(list);
            } else {
                grouping.setGroups(new ArrayList<>(list));
            }
        } else if (grouping.getGroups() != null) {
            grouping.setGroups(null);
        }
        
        // Set remaining fields with conditionals as necessary
        request.getHasLeader().ifPresent(grouping::setHasLeader);
        Optional.ofNullable(request.getId()).filter(id -> id != null && !id.isEmpty()).ifPresent(grouping::setId);
        Optional.of(request.getMaxSize()).filter(size -> size != 0).ifPresent(grouping::setMaxSize);
        Optional.of(request.getMinSize()).filter(size -> size != 0).ifPresent(grouping::setMinSize);
        Optional.ofNullable(request.getStartRegisterDate()).ifPresent(grouping::setStartRegisterDate);
        Optional.ofNullable(request.getEndRegisterDate()).ifPresent(grouping::setEndRegisterDate);

        return grouping;
    }
}

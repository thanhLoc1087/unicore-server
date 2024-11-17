package com.unicore.classevent_service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.unicore.classevent_service.dto.request.QueryCreationRequest;
import com.unicore.classevent_service.dto.request.QueryUpdateRequest;
import com.unicore.classevent_service.dto.response.QueryResponse;
import com.unicore.classevent_service.entity.Query;
import com.unicore.classevent_service.entity.QueryOption;

@Component
public class QueryMapper {
    public Query toQuery(QueryCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Query.QueryBuilder query = Query.builder();

        if ( request.getAllowMultiple() != null ) {
            query.allowMultiple( request.getAllowMultiple() );
        }
        if ( request.getAllowSuggestion() != null ) {
            query.allowSuggestion( request.getAllowSuggestion() );
        }
        query.classId( request.getClassId() );
        query.subclassCode( request.getSubclassCode() );
        query.description( request.getDescription() );
        query.endDate( request.getEndDate() );
        query.name( request.getName() );
        List<QueryOption> list = request.getOptions();
        if ( list != null ) {
            query.options( new ArrayList<>( list ) );
        }
        query.startDate( request.getStartDate() );

        return query.build();
    }

    public Query toQuery(Query query, QueryUpdateRequest request) {
        if (request == null) {
            return query;
        }
    
        if (request.getAllowMultiple() != null) {
            query.setAllowMultiple(request.getAllowMultiple());
        }
        if (request.getAllowSuggestion() != null) {
            query.setAllowSuggestion(request.getAllowSuggestion());
        }
        if (request.getDescription() != null) {
            query.setDescription(request.getDescription());
        }
        if (request.getEndDate() != null) {
            query.setEndDate(request.getEndDate());
        }
        if (request.getName() != null) {
            query.setName(request.getName());
        }
        if (request.getOptions() != null) {
            if (query.getOptions() != null) {
                query.getOptions().clear();
                query.getOptions().addAll(request.getOptions());
            } else {
                query.setOptions(new ArrayList<>(request.getOptions()));
            }
        } else if (query.getOptions() != null) {
            query.setOptions(null);
        }
        if (request.getStartDate() != null) {
            query.setStartDate(request.getStartDate());
        }
    
        return query;
    }
    

    public QueryResponse toQueryResponse(Query query) {
        if ( query == null ) {
            return null;
        }

        QueryResponse.QueryResponseBuilder queryResponse = QueryResponse.builder();

        queryResponse.allowMultiple( query.isAllowMultiple() );
        queryResponse.allowSuggestion( query.isAllowSuggestion() );
        queryResponse.description( query.getDescription() );
        queryResponse.endDate( query.getEndDate() );
        queryResponse.id( query.getId() );
        queryResponse.name( query.getName() );
        queryResponse.classId( query.getClassId() );
        queryResponse.subclassCode( query.getSubclassCode() );
        List<QueryOption> list = query.getOptions();
        if ( list != null ) {
            queryResponse.options( new ArrayList<>( list ) );
        }
        queryResponse.startDate( query.getStartDate() );

        return queryResponse.build();
    }
}

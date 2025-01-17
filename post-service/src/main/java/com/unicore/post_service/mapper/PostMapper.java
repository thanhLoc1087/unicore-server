package com.unicore.post_service.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Editor;
import com.unicore.post_service.entity.Post;

@Component
public class PostMapper {
    public PostResponse toPostResponse(Post post) {
        
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.id( post.getId() );
        postResponse.name( post.getName() );
        postResponse.description( post.getDescription() );
        postResponse.sourceId( post.getSourceId() );
        if ( post.getCreatedDate() != null ) {
            postResponse.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( post.getCreatedDate() ) );
        }
        postResponse.createdBy( post.getCreatedBy() );
        postResponse.creatorEmail( post.getCreatorEmail() );
        List<Editor> list = post.getModifiedBy();
        if ( list != null ) {
            postResponse.modifiedBy( new ArrayList<>( list ) );
        }
        postResponse.type( post.getType() );

        return postResponse.build();
    }

    public void updatePost(Post post, PostRequest request) {
        
        if ( request == null ) {
            return;
        }

        post.setSourceId( request.getSourceId() );
        post.setName( request.getName() );
        post.setDescription( request.getDescription() );
        post.setCreatedBy( request.getCreatedBy() );
        post.setCreatorEmail( request.getCreatorEmail() );
        if ( post.getCategoryIds() != null ) {
            List<String> list = request.getCategoryIds();
            if ( list != null ) {
                post.getCategoryIds().clear();
                post.getCategoryIds().addAll( list );
            }
            else {
                post.setCategoryIds( null );
            }
        }
        else {
            List<String> list = request.getCategoryIds();
            if ( list != null ) {
                post.setCategoryIds( new ArrayList<String>( list ) );
            }
        }
    }
}

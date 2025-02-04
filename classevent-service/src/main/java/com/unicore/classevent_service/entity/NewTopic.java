package com.unicore.classevent_service.entity;

import java.text.Normalizer;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.TopicStatus;
import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class NewTopic {
    @Id
    private String id;
    private String name;
    private String description;
    private String note;
    
    @JsonProperty("teacher_mails")
    private List<String> teacherMails;
    
    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;

    @JsonProperty("teacher_names")
    private List<String> teacherNames;

    @Builder.Default
    private TopicStatus status = TopicStatus.APPROVED;

    private TopicType type; 

    public String genId() {
        // Convert to lowercase
        String result = name.toLowerCase();
        
        // Normalize and remove diacritical marks (accents)
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{M}", "");
        
        // Replace spaces and special characters with underscores
        result = result.replaceAll("[^a-z0-9\\s]", ""); // Keep only alphanumerics and spaces
        result = result.replaceAll("\\s+", "_"); // Replace spaces with underscores
        
        // Return the cleaned result
        return result;
    }

    public static String genId(String name) {
        // Convert to lowercase
        String result = name.toLowerCase();
        
        // Normalize and remove diacritical marks (accents)
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{M}", "");
        
        // Replace spaces and special characters with underscores
        result = result.replaceAll("[^a-z0-9\\s]", ""); // Keep only alphanumerics and spaces
        result = result.replaceAll("\\s+", "_"); // Replace spaces with underscores
        
        // Return the cleaned result
        return result;
    }
}

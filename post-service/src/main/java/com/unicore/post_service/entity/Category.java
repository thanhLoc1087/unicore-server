package com.unicore.post_service.entity;

import java.text.Normalizer;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "post")
public class Category {
    @MongoId
    private String id;
    private String code;
    private String name;

    public static String genCode(String name) {
        // Normalize the string to remove diacritics
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        // Remove diacritic marks using regex
        String noDiacritics = normalized.replaceAll("\\p{M}", "");
        // Replace spaces with underscores and convert to lowercase
        return noDiacritics.replaceAll(" ", "_").toLowerCase();
    }
}

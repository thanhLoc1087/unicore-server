package com.unicore.classroom_service.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String removeVietnameseDiacritics(String text) {
        if (text == null) return null;

        // Normalize text to decomposed form
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);

        // Remove diacritical marks
        String withoutDiacritics = normalizedText.replaceAll("\\p{M}", "");

        // Replace the special 'đ' character with 'd'
        withoutDiacritics = withoutDiacritics.replace("đ", "d").replace("Đ", "D");

        return withoutDiacritics;
    }
}

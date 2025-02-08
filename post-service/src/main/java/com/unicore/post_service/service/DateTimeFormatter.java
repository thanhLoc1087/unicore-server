package com.unicore.post_service.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class DateTimeFormatter {
    Map<Long, Function<LocalDateTime, String>> strategyMap = new LinkedHashMap<>();

    public DateTimeFormatter() {
        strategyMap.put(60L, this::formatInSeconds);
        strategyMap.put(3600L, this::formatInMinutes);
        strategyMap.put(86400L, this::formatInHours);
        strategyMap.put(Long.MAX_VALUE, this::formatInDate);
    }
    
    public String format(LocalDateTime dateTime) {
        long elapseSeconds = ChronoUnit.SECONDS.between(dateTime, LocalDateTime.now());
        var strategy = strategyMap.entrySet()
            .stream()
            .filter(longFunctionEntry -> elapseSeconds < longFunctionEntry.getKey())
            .findFirst().get();
        return strategy.getValue().apply(dateTime);
    }
    
    private String formatInSeconds(LocalDateTime dateTime) {
        long elapseSeconds = ChronoUnit.SECONDS.between(dateTime, LocalDateTime.now());
        return elapseSeconds + " seconds ago.";
    }

    private String formatInMinutes(LocalDateTime dateTime) {
        long elapseMinutes = ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now());
        return elapseMinutes + " minutes ago.";
    }

    private String formatInHours(LocalDateTime dateTime) {
        long elapseHours = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
        return elapseHours + " hours ago.";
    }

    private String formatInDate(LocalDateTime dateTime) {
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ISO_DATE;
        return dateTime.format(dateTimeFormatter);
    }
}

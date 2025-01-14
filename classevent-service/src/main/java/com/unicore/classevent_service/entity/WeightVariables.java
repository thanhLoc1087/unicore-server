package com.unicore.classevent_service.entity;

import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightVariables {
    private WeightType type;
    @Builder.Default
    private Float remaining = 100f;
}

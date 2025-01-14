package com.unicore.classevent_service.entity;

import java.util.ArrayList;
import java.util.List;

import com.unicore.classevent_service.enums.ClassType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubclassRemainingWeight {
    private String subclassCode;
    private ClassType type;
    private List<WeightVariables> weights = new ArrayList<>();
}

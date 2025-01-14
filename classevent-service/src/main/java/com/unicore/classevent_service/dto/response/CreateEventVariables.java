package com.unicore.classevent_service.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.unicore.classevent_service.entity.SubclassRemainingWeight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventVariables {
    private String classId;
    private List<SubclassRemainingWeight> weights = new ArrayList<>();
}

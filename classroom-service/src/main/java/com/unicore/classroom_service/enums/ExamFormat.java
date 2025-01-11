package com.unicore.classroom_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExamFormat {
    TU_LUAN("TU_LUAN",true),
    TRAC_NGHIEM("TRAC_NGHIEM",true),
    THUC_HANH("THUC_HANH",false),
    VAN_DAP("VAN_DAP",false),
    DO_AN("DO_AN",false),
    DO_AN_TOT_NGHIEP("DO_AN_TOT_NGHIEP",true),
    NONE("NONE", false);

    
    @JsonValue
    public String getCode() {
        return code;
    }
    private String code;
    private boolean isOrgManaged;

    @JsonCreator
    public static ExamFormat fromCode(String code) {
        switch (code) {
            case "TL", "TU_LUAN":
                return TU_LUAN;
            case "TN", "TRAC_NGHIEM":
                return TRAC_NGHIEM;
            case "TH", "THUC_HANH":
                return THUC_HANH;
            case "VD", "VAN_DAP":
                return VAN_DAP;
            case "DA", "DO_AN":
                return DO_AN;
            case "DATN", "DO_AN_TOT_NGHIEP":
                return DO_AN_TOT_NGHIEP;
            default:
                return NONE;
        }
    }
}

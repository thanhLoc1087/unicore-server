package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExamFormat {
    TU_LUAN("TL", true, false),
    TRAC_NGHIEM("TN", true, false),
    THUC_HANH("TH", false, false),
    VAN_DAP("VD", false, false),
    DO_AN("DA", false, true),
    DO_AN_TOT_NGHIEP("DATN", true, true);

    private String code;
    private boolean isOrgManaged;
    private boolean isProject;

    @JsonCreator
    public static ExamFormat fromCode(String code) {
        switch (code) {
            case "TL":
                return TU_LUAN;
            case "TN":
                return TRAC_NGHIEM;
            case "TH":
                return THUC_HANH;
            case "VD":
                return VAN_DAP;
            case "DA":
                return DO_AN;
            case "DATN":
                return DO_AN_TOT_NGHIEP;
            default:
                throw new IllegalArgumentException("Unknown code: " + code);
        }
    }
}

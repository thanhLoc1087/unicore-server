package com.unicore.classroom_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExamFormat {
    TU_LUAN(true),
    TRAC_NGHIEM(true),
    THUC_HANH(false),
    VAN_DAP(false),
    DO_AN(false),
    DO_AN_TOT_NGHIEP(true);

    private boolean isOrgManaged;

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

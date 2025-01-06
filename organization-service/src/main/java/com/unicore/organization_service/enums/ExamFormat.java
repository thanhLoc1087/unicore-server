package com.unicore.organization_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum ExamFormat {
    TU_LUAN(true),
    TRAC_NGHIEM(true),
    THUC_HANH(false),
    VAN_DAP(false),
    DO_AN(false),
    DO_AN_TOT_NGHIEP(true),
    NONE(false);

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
                log.info("Unknown code: " + code);
                return NONE;
        }
    }
}

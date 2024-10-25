package com.unicore.organization_service.enums;

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
}

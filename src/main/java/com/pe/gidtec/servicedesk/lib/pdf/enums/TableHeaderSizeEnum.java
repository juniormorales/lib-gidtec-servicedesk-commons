package com.pe.gidtec.servicedesk.lib.pdf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableHeaderSizeEnum {

    SMALL(1f),
    MEDIUM(2f),
    LARGE(3f),
    XLARGE(4f);
    private final float size;
}

package com.pe.gidtec.servicedesk.lib.pdf.model;

import com.pe.gidtec.servicedesk.lib.pdf.enums.TableHeaderSizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TableHeaderEntity {

    private String nameCellHeader;
    private TableHeaderSizeEnum sizeCellColumn;
}

package com.pe.gidtec.servicedesk.lib.pdf.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class TableEntityIterator {

    private Integer numberColumns = 0;

    public TableEntityIterator(TableHeaderEntity[] args) {
        namesTitleColumn = Arrays.asList(args);
        numberColumns = namesTitleColumn.size();
    }

    public void setData(String [][] args) {
        List<List<String>> aux = new ArrayList<>();
        for(String [] element: args){
            aux.add(Arrays.asList(element));
        }
        valuesInRow = aux;
        assert valuesInRow.size() == numberColumns;

    }

    public List<TableHeaderEntity> namesTitleColumn;
    public List<List<String>> valuesInRow;
}

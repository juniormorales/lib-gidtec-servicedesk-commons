package com.pe.gidtec.servicedesk.lib.pdf.builder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.pe.gidtec.servicedesk.lib.pdf.model.TableEntityIterator;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class PdfGenerator {

    Document document = null;
    String nameDocument = null;

    public String getNameDocument(){
        return nameDocument;
    }

    public byte[] getBytes(Boolean deleteDocument){
        Path path = Paths.get("src/main/resources/" + nameDocument);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            log.error(e.getMessage());
        }
        if(deleteDocument){
            File myObj = new File("src/main/resources/" + nameDocument);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        return content;
    }

    private PdfGenerator(Builder builder){
        nameDocument = UUID.randomUUID().toString() + ".pdf";
        document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
        this.generate();
        builder.titles.forEach(this::setTitle);
        builder.tables.forEach(this::setTable);
        this.close();
    }

    private void generate(){

        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/" + nameDocument));
            document.open();
        } catch (DocumentException | FileNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private void close() {
        document.close();
        log.info("Documento creado exitosamente");
    }

    private void setTitle(String title){

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(title, font);

        Phrase phrase = new Phrase();
        phrase.add(chunk);

        Paragraph paragraph = new Paragraph();
        paragraph.add(phrase);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        try {
            document.add(paragraph);
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    private void setTable(TableEntityIterator iterator) {
        List<Float> sizes = iterator.getNamesTitleColumn()
                .stream()
                .map(tableHeaderEntity -> tableHeaderEntity.getSizeCellColumn().getSize())
                .collect(Collectors.toList());
        float[] sizesFloat = new float[sizes.size()];
        for(int i=0; i< sizes.size(); i++) {
            sizesFloat[i] = sizes.get(i);
        }

        PdfPTable table = new PdfPTable(iterator.getNumberColumns());
        table.setWidthPercentage(100);
        try {
            table.setTotalWidth(sizesFloat);
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        Font font = new Font();
        font.setSize(10.0f);
        font.setFamily(Font.FontFamily.TIMES_ROMAN.name());
        iterator.getNamesTitleColumn()
                .forEach(columnTitle -> {
                    Phrase phrase = new Phrase(columnTitle.getNameCellHeader(),font);
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(phrase);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
        iterator.getValuesInRow()
                .forEach(horizontal -> {
                    horizontal.forEach(value -> {
                        setDataInTable(table,value);
                    });
                });
        try {
            document.add(table);
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    private void setDataInTable(PdfPTable table, String value) {
        value = Objects.nonNull(value)?value:"";
        Font font = new Font();
        font.setSize(7.0f);
        font.setFamily(Font.FontFamily.TIMES_ROMAN.name());
        Phrase phrase = new Phrase(value,font);
        PdfPCell verticalAlignCell = new PdfPCell(phrase);
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(verticalAlignCell);
    }

    public static class Builder{

        private List<TableEntityIterator> tables;
        private List<String> titles;

        public Builder(){
            tables = new ArrayList<>();
            titles = new ArrayList<>();
        }

        public Builder addTitle(String title) {
            this.titles.add(title);
            return this;
        }

        public Builder addTable(TableEntityIterator iterator) {
            this.tables.add(iterator);
            return this;
        }

        public PdfGenerator build(){
            return new PdfGenerator(this);
        }

    }
}

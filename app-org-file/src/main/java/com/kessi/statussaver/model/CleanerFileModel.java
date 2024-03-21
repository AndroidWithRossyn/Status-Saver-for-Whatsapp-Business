package com.kessi.statussaver.model;

public class CleanerFileModel {
    private String filename;
    private String filepath;
    public boolean selected = false;
    private String size;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected2) {
        this.selected = selected2;
    }

    public CleanerFileModel(String filepath, String filename, String size) {
        this.filepath = filepath;
        this.filename = filename;
        this.size = size;
    }

    public String getFileName() {
        return this.filename;
    }

    public String getFilePath() {
        return this.filepath;
    }

    public String getSize() {
        return this.size;
    }

    public void setFileName(String paramString) {
        this.filename = paramString;
    }

    public void setFilePath(String paramString) {
        this.filepath = paramString;
    }

    public void setSize(String size2) {
        this.size = size2;
    }
}

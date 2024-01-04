package com.banrossyn.socialsaver.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FileModel implements Parcelable {
    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };
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

    public FileModel(String paramString1, String paramString2) {
        this.filepath = paramString1;
        this.filename = paramString2;
    }

    protected FileModel(Parcel in) {
        this.filename = in.readString();
        this.filepath = in.readString();
    }

    public String getFileName() {
        return this.filename;
    }

    public String getFilePath() {
        return this.filepath;
    }

    public void setFileName(String paramString) {
        this.filename = paramString;
    }

    public void setFilePath(String paramString) {
        this.filepath = paramString;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.filename);
        parcel.writeString(this.filepath);
    }
}

package com.stm.common.dto;


import android.net.Uri;

import java.io.File;

import java.io.Serializable;

/**
 * 파일 정보 data transfer object 클래스
 * <p>
 * 파일 정보, 파일 uri, 파일 path
 */

public class FileDto implements Serializable{
    private Uri uri;
    private String path;
    private int type;
    private File file;
    private File thumbnail;

    public FileDto() {
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(File thumbnail) {
        this.thumbnail = thumbnail;
    }


}

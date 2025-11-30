package com.jc.photo.model;

import java.io.File;

/**
 * 媒体文件信息模型
 */
public class MediaFileInfo {
    private String name;
    private long size;
    private String sizeFormatted;
    private String format;
    private String path;
    
    public MediaFileInfo() {
    }
    
    public MediaFileInfo(File file) {
        this.name = file.getName();
        this.size = file.length();
        this.sizeFormatted = formatFileSize(size);
        this.format = getFileExtension(file);
        this.path = file.getAbsolutePath();
    }
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toUpperCase();
        }
        return null;
    }
    
    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public String getSizeFormatted() {
        return sizeFormatted;
    }
    
    public void setSizeFormatted(String sizeFormatted) {
        this.sizeFormatted = sizeFormatted;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
}


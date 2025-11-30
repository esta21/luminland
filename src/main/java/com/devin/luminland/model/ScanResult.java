package com.devin.luminland.model;

import java.util.*;

/**
 * 扫描结果模型
 */
public class ScanResult {
    private int totalFiles;
    private long totalSize;
    private String totalSizeFormatted;
    private Map<String, Integer> formats;
    private Map<String, Long> formatSizes;
    private Map<String, List<MediaFileInfo>> photosByFormat;
    
    public ScanResult() {
        this.formats = new HashMap<>();
        this.formatSizes = new HashMap<>();
        this.photosByFormat = new HashMap<>();
    }
    
    // Getters and Setters
    public int getTotalFiles() {
        return totalFiles;
    }
    
    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }
    
    public long getTotalSize() {
        return totalSize;
    }
    
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    
    public String getTotalSizeFormatted() {
        return totalSizeFormatted;
    }
    
    public void setTotalSizeFormatted(String totalSizeFormatted) {
        this.totalSizeFormatted = totalSizeFormatted;
    }
    
    public Map<String, Integer> getFormats() {
        return formats;
    }
    
    public void setFormats(Map<String, Integer> formats) {
        this.formats = formats;
    }
    
    public Map<String, Long> getFormatSizes() {
        return formatSizes;
    }
    
    public void setFormatSizes(Map<String, Long> formatSizes) {
        this.formatSizes = formatSizes;
    }
    
    public Map<String, List<MediaFileInfo>> getPhotosByFormat() {
        return photosByFormat;
    }
    
    public void setPhotosByFormat(Map<String, List<MediaFileInfo>> photosByFormat) {
        this.photosByFormat = photosByFormat;
    }
}


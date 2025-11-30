package com.devin.luminland.model;

import java.util.*;

/**
 * 整理结果模型
 */
public class OrganizeResult {
    private int totalFiles;
    private Map<String, Integer> formats;
    private List<MovedFileInfo> movedFiles;
    private String message;
    
    public OrganizeResult() {
        this.formats = new HashMap<>();
        this.movedFiles = new ArrayList<>();
    }
    
    // Getters and Setters
    public int getTotalFiles() {
        return totalFiles;
    }
    
    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }
    
    public Map<String, Integer> getFormats() {
        return formats;
    }
    
    public void setFormats(Map<String, Integer> formats) {
        this.formats = formats;
    }
    
    public List<MovedFileInfo> getMovedFiles() {
        return movedFiles;
    }
    
    public void setMovedFiles(List<MovedFileInfo> movedFiles) {
        this.movedFiles = movedFiles;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 已移动文件信息
     */
    public static class MovedFileInfo {
        private String originalName;
        private String format;
        private String newPath;
        
        public MovedFileInfo() {
        }
        
        public MovedFileInfo(String originalName, String format, String newPath) {
            this.originalName = originalName;
            this.format = format;
            this.newPath = newPath;
        }
        
        // Getters and Setters
        public String getOriginalName() {
            return originalName;
        }
        
        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }
        
        public String getFormat() {
            return format;
        }
        
        public void setFormat(String format) {
            this.format = format;
        }
        
        public String getNewPath() {
            return newPath;
        }
        
        public void setNewPath(String newPath) {
            this.newPath = newPath;
        }
    }
}


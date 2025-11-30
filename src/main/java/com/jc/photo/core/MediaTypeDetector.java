package com.jc.photo.core;

import java.io.File;
import java.util.*;

/**
 * 媒体类型检测器
 * 用于识别照片和视频文件格式
 */
public class MediaTypeDetector {
    
    // 支持的图片格式
    private static final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "tiff", "tif", 
        "cr3", "cr2", "raw", "nef", "orf", "arw", "dng", 
        "raf", "rw2", "pef", "srw", "x3f", "heic", "heif"
    ));
    
    // 支持的视频格式
    private static final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList(
        "mp4", "avi", "mov", "mkv", "wmv", "flv", "webm", 
        "m4v", "mpg", "mpeg", "3gp", "3g2", "asf", "rm", 
        "rmvb", "vob", "ts", "mts", "m2ts", "f4v", "ogv"
    ));
    
    /**
     * 检查是否为照片文件
     */
    public static boolean isPhotoFile(File file) {
        String extension = getFileExtension(file);
        return extension != null && IMAGE_EXTENSIONS.contains(extension.toLowerCase());
    }
    
    /**
     * 检查是否为视频文件
     */
    public static boolean isVideoFile(File file) {
        String extension = getFileExtension(file);
        return extension != null && VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }
    
    /**
     * 检查是否为媒体文件（照片或视频）
     */
    public static boolean isMediaFile(File file) {
        return isPhotoFile(file) || isVideoFile(file);
    }
    
    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }
    
    /**
     * 获取所有支持的图片格式
     */
    public static Set<String> getSupportedImageFormats() {
        return new HashSet<>(IMAGE_EXTENSIONS);
    }
    
    /**
     * 获取所有支持的视频格式
     */
    public static Set<String> getSupportedVideoFormats() {
        return new HashSet<>(VIDEO_EXTENSIONS);
    }
    
    /**
     * 获取所有支持的媒体格式
     */
    public static Set<String> getSupportedMediaFormats() {
        Set<String> allFormats = new HashSet<>();
        allFormats.addAll(IMAGE_EXTENSIONS);
        allFormats.addAll(VIDEO_EXTENSIONS);
        return allFormats;
    }
}


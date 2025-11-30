package com.devin.luminland.service;

import com.devin.luminland.core.FileUtils;
import com.devin.luminland.core.MediaTypeDetector;
import com.devin.luminland.model.MediaFileInfo;
import com.devin.luminland.model.ScanResult;

import java.io.File;
import java.util.*;

/**
 * 媒体文件扫描服务
 * 负责扫描目录中的照片和视频文件
 */
public class MediaScanService {
    
    /**
     * 扫描指定目录的媒体文件
     */
    public ScanResult scanDirectory(File sourceDir) {
        ScanResult result = new ScanResult();
        
        if (sourceDir == null || !sourceDir.exists() || !sourceDir.isDirectory()) {
            return result;
        }
        
        File[] files = sourceDir.listFiles();
        if (files == null) {
            return result;
        }
        
        Map<String, List<MediaFileInfo>> mediaByFormat = new HashMap<>();
        
        // 扫描所有照片和视频文件
        for (File file : files) {
            if (file.isFile() && MediaTypeDetector.isMediaFile(file)) {
                String extension = MediaTypeDetector.getFileExtension(file);
                if (extension != null) {
                    extension = extension.toLowerCase();
                    mediaByFormat.computeIfAbsent(extension, k -> new ArrayList<>());
                    mediaByFormat.get(extension).add(new MediaFileInfo(file));
                }
            }
        }
        
        // 统计信息
        Map<String, Integer> formatCounts = new HashMap<>();
        Map<String, Long> formatSizes = new HashMap<>();
        
        for (Map.Entry<String, List<MediaFileInfo>> entry : mediaByFormat.entrySet()) {
            String format = entry.getKey();
            List<MediaFileInfo> fileList = entry.getValue();
            String formatUpper = format.toUpperCase();
            
            formatCounts.put(formatUpper, fileList.size());
            
            long totalSize = fileList.stream()
                .mapToLong(MediaFileInfo::getSize)
                .sum();
            formatSizes.put(formatUpper, totalSize);
        }
        
        int totalCount = formatCounts.values().stream().mapToInt(Integer::intValue).sum();
        long totalSize = formatSizes.values().stream().mapToLong(Long::longValue).sum();
        
        result.setTotalFiles(totalCount);
        result.setTotalSize(totalSize);
        result.setTotalSizeFormatted(FileUtils.formatFileSize(totalSize));
        result.setFormats(formatCounts);
        result.setFormatSizes(formatSizes);
        result.setPhotosByFormat(mediaByFormat);
        
        return result;
    }
}


package com.jc.photo.service;

import com.jc.photo.core.FileUtils;
import com.jc.photo.core.MediaTypeDetector;
import com.jc.photo.core.PhotoMetadataExtractor;
import com.jc.photo.model.OrganizeResult;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * 媒体文件整理服务
 * 负责按年月->格式分类整理照片和视频文件
 */
public class MediaOrganizeService {
    
    /**
     * 整理指定目录的媒体文件
     * 按年月->格式的层级结构进行分类
     */
    public OrganizeResult organizeDirectory(File sourceDir) throws Exception {
        OrganizeResult result = new OrganizeResult();
        
        if (sourceDir == null || !sourceDir.exists() || !sourceDir.isDirectory()) {
            result.setMessage("目录不存在或无效");
            return result;
        }
        
        File[] files = sourceDir.listFiles();
        if (files == null) {
            result.setMessage("无法读取目录");
            return result;
        }
        
        // 按年月分组文件
        Map<String, List<File>> mediaByDate = new HashMap<>();
        List<OrganizeResult.MovedFileInfo> movedFiles = new ArrayList<>();
        
        // 扫描所有照片和视频文件
        for (File file : files) {
            if (file.isFile() && MediaTypeDetector.isMediaFile(file)) {
                // 获取文件的拍摄日期（年月）
                String date = PhotoMetadataExtractor.getPhotoDate(file);
                if (date == null) {
                    // 如果无法获取日期，使用"未知日期"作为默认值
                    date = "未知日期";
                }
                
                mediaByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(file);
            }
        }
        
        if (mediaByDate.isEmpty()) {
            result.setMessage("未找到照片或视频文件");
            return result;
        }
        
        // 按年月和格式移动文件
        for (Map.Entry<String, List<File>> dateEntry : mediaByDate.entrySet()) {
            String date = dateEntry.getKey();
            List<File> filesForDate = dateEntry.getValue();
            
            // 按格式进一步分组
            Map<String, List<File>> filesByFormat = new HashMap<>();
            for (File file : filesForDate) {
                String extension = MediaTypeDetector.getFileExtension(file);
                if (extension != null) {
                    extension = extension.toLowerCase();
                    filesByFormat.computeIfAbsent(extension, k -> new ArrayList<>()).add(file);
                }
            }
            
            // 为每个年月创建目录
            File dateDir = new File(sourceDir, date);
            FileUtils.ensureDirectoryExists(dateDir);
            
            // 按格式移动文件到对应的子目录
            for (Map.Entry<String, List<File>> formatEntry : filesByFormat.entrySet()) {
                String format = formatEntry.getKey();
                List<File> filesToMove = formatEntry.getValue();
                
                File targetDir = new File(dateDir, format.toUpperCase());
                FileUtils.ensureDirectoryExists(targetDir);
                
                for (File file : filesToMove) {
                    Path targetPath = FileUtils.moveFile(file, targetDir);
                    OrganizeResult.MovedFileInfo movedFile = new OrganizeResult.MovedFileInfo(
                        file.getName(),
                        date + "/" + format.toUpperCase(),  // 更新格式字段以反映新的层级结构
                        targetPath.toString()
                    );
                    movedFiles.add(movedFile);
                }
            }
        }
        
        // 统计信息
        Map<String, Integer> dateCounts = new HashMap<>();
        for (Map.Entry<String, List<File>> entry : mediaByDate.entrySet()) {
            dateCounts.put(entry.getKey(), entry.getValue().size());
        }
        
        result.setTotalFiles(movedFiles.size());
        result.setFormats(dateCounts);  // 使用日期作为统计维度
        result.setMovedFiles(movedFiles);
        result.setMessage("成功整理 " + movedFiles.size() + " 个文件，按年月和格式分类");
        
        return result;
    }
}
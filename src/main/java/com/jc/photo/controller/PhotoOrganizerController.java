package com.jc.photo.controller;

import com.jc.photo.model.MediaFileInfo;
import com.jc.photo.model.OrganizeResult;
import com.jc.photo.model.ScanResult;
import com.jc.photo.service.MediaOrganizeService;
import com.jc.photo.service.MediaScanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

/**
 * 照片处理工具 REST API 控制器
 * 提供照片和视频的扫描、整理等处理功能
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PhotoOrganizerController {
    
    private final MediaScanService scanService;
    private final MediaOrganizeService organizeService;
    
    public PhotoOrganizerController() {
        this.scanService = new MediaScanService();
        this.organizeService = new MediaOrganizeService();
    }
    
    /**
     * 整理指定目录的照片和视频
     */
    @PostMapping("/organize")
    public ResponseEntity<Map<String, Object>> organizePhotos(@RequestBody Map<String, String> request) {
        String sourcePath = request.get("path");
        
        Map<String, Object> response = new HashMap<>();
        
        if (sourcePath == null || sourcePath.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "路径不能为空");
            return ResponseEntity.badRequest().body(response);
        }
        
        File sourceDir = new File(sourcePath);
        
        if (!sourceDir.exists()) {
            response.put("success", false);
            response.put("message", "指定的路径不存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (!sourceDir.isDirectory()) {
            response.put("success", false);
            response.put("message", "指定的路径不是一个目录");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            OrganizeResult result = organizeService.organizeDirectory(sourceDir);
            response.put("success", true);
            response.put("message", result.getMessage());
            response.put("data", convertOrganizeResultToMap(result));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "处理失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 扫描指定目录的照片和视频信息
     */
    @PostMapping("/scan")
    public ResponseEntity<Map<String, Object>> scanPhotos(@RequestBody Map<String, String> request) {
        String sourcePath = request.get("path");
        
        Map<String, Object> response = new HashMap<>();
        
        if (sourcePath == null || sourcePath.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "路径不能为空");
            return ResponseEntity.badRequest().body(response);
        }
        
        File sourceDir = new File(sourcePath);
        
        if (!sourceDir.exists()) {
            response.put("success", false);
            response.put("message", "指定的路径不存在");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (!sourceDir.isDirectory()) {
            response.put("success", false);
            response.put("message", "指定的路径不是一个目录");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            ScanResult scanResult = scanService.scanDirectory(sourceDir);
            response.put("success", true);
            response.put("data", convertScanResultToMap(scanResult));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "扫描失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 将 ScanResult 转换为 Map（用于 JSON 响应）
     */
    private Map<String, Object> convertScanResultToMap(ScanResult result) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalFiles", result.getTotalFiles());
        map.put("totalSize", result.getTotalSize());
        map.put("totalSizeFormatted", result.getTotalSizeFormatted());
        map.put("formats", result.getFormats());
        map.put("formatSizes", result.getFormatSizes());
        
        // 转换 photosByFormat
        Map<String, List<Map<String, Object>>> photosByFormatMap = new HashMap<>();
        for (Map.Entry<String, List<MediaFileInfo>> entry : result.getPhotosByFormat().entrySet()) {
            List<Map<String, Object>> fileList = new ArrayList<>();
            for (MediaFileInfo fileInfo : entry.getValue()) {
                Map<String, Object> fileMap = new HashMap<>();
                fileMap.put("name", fileInfo.getName());
                fileMap.put("size", fileInfo.getSize());
                fileMap.put("sizeFormatted", fileInfo.getSizeFormatted());
                fileList.add(fileMap);
            }
            photosByFormatMap.put(entry.getKey(), fileList);
        }
        map.put("photosByFormat", photosByFormatMap);
        
        return map;
    }
    
    /**
     * 将 OrganizeResult 转换为 Map（用于 JSON 响应）
     */
    private Map<String, Object> convertOrganizeResultToMap(OrganizeResult result) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalFiles", result.getTotalFiles());
        map.put("formats", result.getFormats());
        map.put("message", result.getMessage());
        
        // 转换 movedFiles
        List<Map<String, String>> movedFilesList = new ArrayList<>();
        for (OrganizeResult.MovedFileInfo movedFile : result.getMovedFiles()) {
            Map<String, String> fileMap = new HashMap<>();
            fileMap.put("originalName", movedFile.getOriginalName());
            fileMap.put("format", movedFile.getFormat());
            fileMap.put("newPath", movedFile.getNewPath());
            movedFilesList.add(fileMap);
        }
        map.put("movedFiles", movedFilesList);
        
        return map;
    }
}


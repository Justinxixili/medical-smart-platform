package com.medical.file.minio.controller;


import com.medical.file.minio.service.FileStorageService;
import com.medical.model.common.dtos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 17:45
 */
@RefreshScope
@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload/image")
    public Result<String> uploadImage(@RequestParam(value = "prefix", required = false, defaultValue = "defaultPrefix") String prefix,
                                      @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileStorageService.uploadImgFile(prefix, file.getOriginalFilename(), file.getInputStream());
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    @PostMapping("/upload/html")
    public Result<String> uploadHtml(@RequestParam(value = "prefix", required = false, defaultValue = "defaultPrefix") String prefix,
                                     @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileStorageService.uploadHtmlFile(prefix, file.getOriginalFilename(), file.getInputStream());
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Result<Void> deleteFile(@RequestParam("pathUrl") String pathUrl) {
        try {
            fileStorageService.delete(pathUrl);
            return Result.success();
        } catch (Exception e) {
            return Result.error("文件删除失败：" + e.getMessage());
        }
    }

    @GetMapping("/download")
    public Result<byte[]> downloadFile(@RequestParam("pathUrl") String pathUrl) {
        try {
            byte[] fileData = fileStorageService.downLoadFile(pathUrl);
            return Result.success(fileData);
        } catch (Exception e) {
            return Result.error("文件下载失败：" + e.getMessage());
        }
    }
}
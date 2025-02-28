package com.example.spring_boot_react_demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile file, String resourceType);
    void deleteFile(String publicId, String resourceType);
    String export(MultipartFile file, String folderName, String resourceType);
}

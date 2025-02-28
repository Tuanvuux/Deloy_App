package com.example.spring_boot_react_demo.service.impl;

import com.cloudinary.Cloudinary;
import com.example.spring_boot_react_demo.model.File;
import com.example.spring_boot_react_demo.service.CloudinaryService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String fileUploadURL1 = "https://res.cloudinary.com/duli95mss/";
    private static final String fileUploadURL2 = "/upload/v1/";
    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String resourceType) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "folder_1");
            options.put("resource_type", resourceType);

            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");

            if (File.video.name().equals(resourceType)) {
                return fileUploadURL1 + File.video.name() + fileUploadURL2 + publicId + ".mp4";
            } else if (File.audio.name().equals(resourceType)) {
                return fileUploadURL1 + File.audio.name() + fileUploadURL2 + publicId + ".mp3";
            } else if (File.image.name().equals(resourceType)) {
                return fileUploadURL1 + File.image.name() + fileUploadURL2 + publicId + ".jpg";
            }
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteFile(String fileUrl, String resourceType) {

        try {
            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", resourceType);
            Map result = cloudinary.uploader().destroy(extractPublicId(fileUrl, resourceType), options);
            String status = (String) result.get("result");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String export(MultipartFile file, String folderName, String resourceType) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            options.put("resource_type", resourceType);
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // Lấy phần đuôi (ví dụ: .mp4, .avi)
            }
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return fileUploadURL1 +File.video.name()+ "/upload/v1/" + publicId + extension;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String extractPublicId(String fileUrl, String resourceType) {
        String prefix = fileUploadURL1 + resourceType + fileUploadURL2;

        if (fileUrl.startsWith(prefix)) {
            String fileName = fileUrl.substring(prefix.length());
            int lastDotIndex = fileName.lastIndexOf(".");

            if (lastDotIndex != -1) {
                return fileName.substring(0, lastDotIndex);
            }
        }
        return null;
    }
}
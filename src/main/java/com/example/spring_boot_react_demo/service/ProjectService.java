package com.example.spring_boot_react_demo.service;

import com.example.spring_boot_react_demo.model.dto.request.CreateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.request.UpdateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.response.BasicProjectResponse;
import com.example.spring_boot_react_demo.model.dto.response.FullProjectResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    BasicProjectResponse createProject(CreateProjectRequest createProjectRequest);
    FullProjectResponse getProject(Long projectId);
    String updateProject(UpdateProjectRequest updateProjectRequest);
    String exportProject(MultipartFile file, String outputVideoPath);
}

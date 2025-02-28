package com.example.spring_boot_react_demo.service.impl;

import com.example.spring_boot_react_demo.exception.AppException;
import com.example.spring_boot_react_demo.exception.ErrorCode;
import com.example.spring_boot_react_demo.model.dto.request.BackgroundRequest;
import com.example.spring_boot_react_demo.model.dto.request.CreateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.request.UpdateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.response.BasicProjectResponse;
import com.example.spring_boot_react_demo.model.dto.response.FullProjectResponse;
import com.example.spring_boot_react_demo.model.entity.Background;
import com.example.spring_boot_react_demo.model.entity.Project;
import com.example.spring_boot_react_demo.model.entity.Video;
import com.example.spring_boot_react_demo.repository.ProjectRepo;
import com.example.spring_boot_react_demo.service.BackgroundService;
import com.example.spring_boot_react_demo.service.CloudinaryService;
import com.example.spring_boot_react_demo.service.FFmpegService;
import com.example.spring_boot_react_demo.service.ProjectService;
import com.example.spring_boot_react_demo.util.EntityMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.spring_boot_react_demo.util.EntityMapper.*;
import static com.example.spring_boot_react_demo.util.FileUtil.getFileType;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {
    ProjectRepo projectRepo;
    BackgroundService backgroundService;
    CloudinaryService cloudinaryService;
    FFmpegService ffmpegService;

    @Override
    public BasicProjectResponse createProject(CreateProjectRequest createProjectRequest) {
        Project project = new Project();
        project.setName(createProjectRequest.getName());
        projectRepo.save(project);
        return mapToBasicProjectResponse(project);
    }

    @Override
    public FullProjectResponse getProject(Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        List<Background> backgrounds = project.getBackgrounds();
        return mapToProjectResponse(project,
                backgrounds.stream()
                        .map(EntityMapper::maptoBackgroundResponse)
                        .toList());
    }

    @Override
    public String updateProject(UpdateProjectRequest updateProjectRequest) {
        Project project = projectRepo.findById(updateProjectRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        project.setName(updateProjectRequest.getName());
        project.setUploadTime(updateProjectRequest.getUploadTime());
        project.setLength(updateProjectRequest.getLength());
        for (BackgroundRequest background : updateProjectRequest.getBackgrounds()) {
            backgroundService.updateBackground(background);
        }
        projectRepo.save(project);
        return "Success";
    }

    @Override
    public String exportProject(MultipartFile file, String outputVideoPath) {
        MultipartFile multipartFile = ffmpegService.convertVideo(file, outputVideoPath);
        return cloudinaryService.export(multipartFile,
                "converted_videos",
                "video");
    }

}

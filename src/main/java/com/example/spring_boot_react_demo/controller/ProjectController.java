package com.example.spring_boot_react_demo.controller;

import com.cloudinary.Api;
import com.example.spring_boot_react_demo.model.dto.request.CreateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.request.UpdateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.response.ApiResponse;
import com.example.spring_boot_react_demo.model.dto.response.BasicProjectResponse;
import com.example.spring_boot_react_demo.model.dto.response.FullProjectResponse;
import com.example.spring_boot_react_demo.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping("/createProject")
    public ApiResponse<BasicProjectResponse> createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        return ApiResponse.<BasicProjectResponse>builder()
                .result(projectService.createProject(createProjectRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<FullProjectResponse> getProjectById(@RequestParam Long id) {
        return ApiResponse.<FullProjectResponse>builder()
                .result(projectService.getProject(id))
                .build();
    }

    @PatchMapping("/updateProject")
    public ApiResponse<String> UpdateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {
        return ApiResponse.<String>builder()
                .result(projectService.updateProject(updateProjectRequest))
                .build();
    }

    @GetMapping("/exportProject")
    public ApiResponse<String> exportProject(@RequestParam("file") MultipartFile file, @RequestParam("outputVideoPath") String outputVideoPath) {
        return ApiResponse.<String>builder()
                .result(projectService.exportProject(file, outputVideoPath))
                .build();
    }

}

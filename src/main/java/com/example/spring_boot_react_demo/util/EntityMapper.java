package com.example.spring_boot_react_demo.util;

import com.example.spring_boot_react_demo.model.dto.response.BackgroundResponse;
import com.example.spring_boot_react_demo.model.dto.response.BasicProjectResponse;
import com.example.spring_boot_react_demo.model.dto.response.FullProjectResponse;
import com.example.spring_boot_react_demo.model.entity.Background;
import com.example.spring_boot_react_demo.model.entity.Project;

import java.util.List;

public class EntityMapper {
    public static BasicProjectResponse mapToBasicProjectResponse(Project project) {
        return new BasicProjectResponse(
                project.getId(),
                project.getName(),
                project.getUploadTime()
        );
    }
    public static FullProjectResponse mapToProjectResponse(Project project, List<BackgroundResponse> backgroundResponses) {
        return new FullProjectResponse(
                project.getId(),
                project.getName(),
                project.getUploadTime(),
                project.getLength(),
                backgroundResponses
        );
    }
    public static BackgroundResponse maptoBackgroundResponse (Background background){
        return new BackgroundResponse(
                background.getId(),
                background.getProject().getId(),
                background.getAsset(),
                background.getType(),
                background.getUploadTime(),
                background.getDisplayOrder(),
                background.getStartTime(),
                background.getEndTime()
        );
    }
}

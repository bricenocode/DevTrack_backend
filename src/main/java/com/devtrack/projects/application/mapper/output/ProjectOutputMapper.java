package com.devtrack.projects.application.mapper.output;

import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectOutputMapper {

    UserOutputSimpleDto userToUserOutputDto(UserEntity user);
    List<UserOutputSimpleDto> usersToUserOutputDtos(List<UserEntity> users);
    List<ProjectOutputFullDto> projectsToProjectFullDtos(List<ProjectEntity> projects);

    TaskOutputSimpleDto taskToTaskOutputSimpleDto(TaskEntity task);
    List<TaskOutputSimpleDto> tasksToTaskOutputSimpleDtos(List<TaskEntity> tasks);

    ProjectOutputFullDto entityToOutputFullDto(ProjectEntity projectEntity);
}

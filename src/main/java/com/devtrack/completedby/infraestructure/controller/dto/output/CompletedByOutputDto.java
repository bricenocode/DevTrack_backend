package com.devtrack.completedby.infraestructure.controller.dto.output;


import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletedByOutputDto {
    private String _id;
    private UserOutputSimpleDto user;
    private TaskStatus status;
}

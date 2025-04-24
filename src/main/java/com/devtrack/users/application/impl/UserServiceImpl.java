package com.devtrack.users.application.impl;

import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.users.application.UserService;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.application.mapper.output.UserOutputMapper;
import com.devtrack.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInputMapper userInputMapper;
    private final UserOutputMapper userOutputMapper;
    private final ProjectRepository projectRepository;

}

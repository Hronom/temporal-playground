package com.github.hronom.temporalplaygroundusers.service;

import com.github.hronom.temporalplaygroundusers.AppConstants;
import com.github.hronom.temporalplaygroundusers.controllers.dto.ConfirmEmailRequestDto;
import com.github.hronom.temporalplaygroundusers.controllers.dto.UserDto;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final WorkflowClient workflowClient;

    @Autowired
    public UserService(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    public void createUser(UserDto userDto) {
        WorkflowOptions options =
                WorkflowOptions
                        .newBuilder()
                        .setTaskQueue(AppConstants.USER_REGISTRATION_TASK_QUEUE)
                        .setWorkflowId("username_" + userDto.username)
                        .build();
        WorkflowStub untyped = workflowClient.newUntypedWorkflowStub("UserRegistrationWorkflow", options);
        untyped.start(userDto);
    }

    public void acceptConfirmation(ConfirmEmailRequestDto confirmEmailRequestDto) {
        WorkflowStub untyped = workflowClient.newUntypedWorkflowStub("username_" + confirmEmailRequestDto.username);
        untyped.signal("acceptConfirmation", confirmEmailRequestDto.token);
    }
}

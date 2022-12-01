package com.github.hronom.temporalplaygroundusers.workflows;

import com.github.hronom.temporalplaygroundusers.controllers.dto.UserDto;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UserRegistrationWorkflow {
    @WorkflowMethod
    void userRegistration(UserDto userDto);

    @QueryMethod
    String getLastEvent();
}

package com.github.hronom.temporalplaygroundusers.workflows;

import com.github.hronom.temporalplaygroundusers.AppConstants;
import com.github.hronom.temporalplaygroundusers.activities.UserRegistrationActivities;
import com.github.hronom.temporalplaygroundusers.activities.dto.UserRegistrationActivitiesResponse;
import com.github.hronom.temporalplaygroundusers.controllers.dto.UserDto;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

@WorkflowImpl(taskQueues = AppConstants.USER_REGISTRATION_TASK_QUEUE)
public class UserRegistrationWorkflowImpl implements UserRegistrationWorkflow {
    private final Logger logger = LoggerFactory.getLogger(UserRegistrationWorkflowImpl.class);

    private UserRegistrationActivities userRegistrationActivities =
            Workflow.newActivityStub(UserRegistrationActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build());

    @Override
    public void userRegistration(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        UserRegistrationActivitiesResponse userRegistrationActivitiesResponse = userRegistrationActivities.notifyNewUserCreated(userId);
        logger.info("Email sent id {}", userRegistrationActivitiesResponse.emailSendId);
    }

    @Override // QueryMethod
    public String getLastEvent() {
        return "ggg";
    }
}

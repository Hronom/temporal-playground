package com.github.hronom.temporalplaygroundusers.workflows;

import com.github.hronom.temporalplaygroundusers.AppConstants;
import com.github.hronom.temporalplaygroundusers.activities.SendUserEmailAdvertisingActivity;
import com.github.hronom.temporalplaygroundusers.activities.UserRegistrationActivities;
import com.github.hronom.temporalplaygroundusers.activities.dto.SentConfirmationEmailResponse;
import com.github.hronom.temporalplaygroundusers.controllers.dto.UserDto;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@WorkflowImpl(taskQueues = AppConstants.USER_MAINTENANCE_TASK_QUEUE)
public class UserEmailAdvertisingWorkflowImpl implements UserEmailAdvertisingWorkflow {
    private final Logger logger = Workflow.getLogger(UserEmailAdvertisingWorkflowImpl.class);

    private SendUserEmailAdvertisingActivity sendUserEmailAdvertisingActivity =
            Workflow.newActivityStub(SendUserEmailAdvertisingActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build());

    @Override
    public void sendAll() {
        logger.info("Start sending advertising emails");
        sendUserEmailAdvertisingActivity.sentConfirmationEmail();
        logger.info("Stop sending advertising emails");
    }
}

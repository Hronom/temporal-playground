package com.github.hronom.temporalplaygroundusers.workflows;

import com.github.hronom.temporalplaygroundusers.AppConstants;
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

@WorkflowImpl(taskQueues = AppConstants.USER_REGISTRATION_TASK_QUEUE)
public class UserRegistrationWorkflowImpl implements UserRegistrationWorkflow {
    private final Logger logger = LoggerFactory.getLogger(UserRegistrationWorkflowImpl.class);

    private final ArrayList<String> incomingConfirmationTokens = new ArrayList<>();

    private UserRegistrationActivities userRegistrationActivities =
            Workflow.newActivityStub(UserRegistrationActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build());

    @Override
    public boolean userRegistration(UserDto userDto) {
        String userId = Workflow.sideEffect(String.class, () -> UUID.randomUUID().toString());

        SentConfirmationEmailResponse sentConfirmationEmailResponse = userRegistrationActivities.sentConfirmationEmail(userId);
        logger.info("Email sent id '{}'", sentConfirmationEmailResponse.confirmationToken);

        Workflow.await(Duration.ofMinutes(1), () -> !incomingConfirmationTokens.isEmpty());

        for (String incomingConfirmationToken : incomingConfirmationTokens) {
            if (Objects.equals(incomingConfirmationToken, sentConfirmationEmailResponse.confirmationToken)) {
                Workflow.sleep(Duration.ofSeconds(5));
                logger.info("Registration confirmed");
                return true;
            }
        }
        logger.info("Registration not confirmed, token expired");
        return false;
    }

    @Override
    public void acceptConfirmation(String token) {
        incomingConfirmationTokens.add(token);
    }

    @Override // QueryMethod
    public String getLastEvent() {
        return "ggg";
    }
}

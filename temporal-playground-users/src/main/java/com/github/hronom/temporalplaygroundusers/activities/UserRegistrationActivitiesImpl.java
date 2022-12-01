package com.github.hronom.temporalplaygroundusers.activities;

import com.github.hronom.temporalplaygroundusers.activities.dto.UserRegistrationActivitiesResponse;
import io.temporal.activity.Activity;
import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.github.hronom.temporalplaygroundusers.AppConstants.USER_REGISTRATION_TASK_QUEUE;

@Component
@ActivityImpl(taskQueues = USER_REGISTRATION_TASK_QUEUE)
public class UserRegistrationActivitiesImpl implements UserRegistrationActivities {
    private final Logger logger = LoggerFactory.getLogger(UserRegistrationActivitiesImpl.class);

    @Override
    public UserRegistrationActivitiesResponse notifyNewUserCreated(String userId) {
        logger.info("Doing work...");
        String emailSentId;
        try {
            // Simulating work
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            emailSentId = UUID.randomUUID().toString();
        } catch (InterruptedException e) {
            throw Activity.wrap(e);
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw Activity.wrap(new IllegalStateException("test error"));
        }
        logger.info("Done work.");
        UserRegistrationActivitiesResponse userRegistrationActivitiesResponse = new UserRegistrationActivitiesResponse();
        userRegistrationActivitiesResponse.emailSent = true;
        userRegistrationActivitiesResponse.emailSendId = emailSentId;
        return userRegistrationActivitiesResponse;
    }
}

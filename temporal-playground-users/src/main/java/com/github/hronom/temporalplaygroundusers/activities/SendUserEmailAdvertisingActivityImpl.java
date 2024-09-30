package com.github.hronom.temporalplaygroundusers.activities;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import static com.github.hronom.temporalplaygroundusers.AppConstants.USER_MAINTENANCE_TASK_QUEUE;

@Component
@ActivityImpl(taskQueues = USER_MAINTENANCE_TASK_QUEUE)
public class SendUserEmailAdvertisingActivityImpl implements SendUserEmailAdvertisingActivity {

    @Override
    public void sentConfirmationEmail() {

    }
}

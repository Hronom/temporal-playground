package com.github.hronom.temporalplaygroundusers.service;

import com.github.hronom.temporalplaygroundusers.workflows.UserEmailAdvertisingWorkflow;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.schedules.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

import static com.github.hronom.temporalplaygroundusers.AppConstants.USER_MAINTENANCE_TASK_QUEUE;

@Service
public class UserEmailAdvertisingService implements ApplicationRunner {
    public final String USER_EMAIL_ADVERTISING_SCHEDULE_ID = "UserEmailAdvertisingSchedule";
    private final ScheduleClient scheduleClient;

    @Autowired
    public UserEmailAdvertisingService(ScheduleClient scheduleClient) {
        this.scheduleClient = scheduleClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (scheduleClient.listSchedules("ScheduleId = '" + USER_EMAIL_ADVERTISING_SCHEDULE_ID + "'", 1).findAny().isEmpty()) {
            Schedule schedule =
                    Schedule.newBuilder()
                            .setAction(
                                    ScheduleActionStartWorkflow.newBuilder()
                                            .setWorkflowType(UserEmailAdvertisingWorkflow.class)
                                            .setOptions(
                                                    WorkflowOptions.newBuilder()
                                                            .setWorkflowId(USER_EMAIL_ADVERTISING_SCHEDULE_ID)
                                                            .setTaskQueue(USER_MAINTENANCE_TASK_QUEUE)
                                                            .build())
                                            .build())
                            .setSpec(
                                    ScheduleSpec.newBuilder()
                                            .setIntervals(Collections.singletonList(new ScheduleIntervalSpec(Duration.ofSeconds(5))))
                                            .build())
                            .build();

            // Create a schedule on the server
            scheduleClient.createSchedule(USER_EMAIL_ADVERTISING_SCHEDULE_ID, schedule, ScheduleOptions.newBuilder().build());
        }
    }
}

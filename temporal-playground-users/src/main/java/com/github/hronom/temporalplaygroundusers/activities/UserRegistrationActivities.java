package com.github.hronom.temporalplaygroundusers.activities;

import com.github.hronom.temporalplaygroundusers.activities.dto.UserRegistrationActivitiesResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UserRegistrationActivities {
    UserRegistrationActivitiesResponse notifyNewUserCreated(String userId);
}

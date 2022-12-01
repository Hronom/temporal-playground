package com.github.hronom.temporalplaygroundusers.activities;

import com.github.hronom.temporalplaygroundusers.activities.dto.SentConfirmationEmailResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UserRegistrationActivities {
    SentConfirmationEmailResponse sentConfirmationEmail(String userId);
}

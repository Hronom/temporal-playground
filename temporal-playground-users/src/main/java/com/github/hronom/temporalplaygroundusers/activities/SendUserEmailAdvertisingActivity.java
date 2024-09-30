package com.github.hronom.temporalplaygroundusers.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SendUserEmailAdvertisingActivity {
    void sentConfirmationEmail();
}

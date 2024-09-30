package com.github.hronom.temporalplaygroundusers.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UserEmailAdvertisingWorkflow {
    @WorkflowMethod
    void sendAll();
}

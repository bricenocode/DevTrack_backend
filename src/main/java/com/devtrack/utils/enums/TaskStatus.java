package com.devtrack.utils.enums;

public enum TaskStatus {
    PENDING("pending"),
    ON_HOLD("onHold"),
    IN_PROGRESS("inProgress"),
    UNDER_REVIEW("underReview"),
    COMPLETED("completed");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskStatus fromValue(String value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
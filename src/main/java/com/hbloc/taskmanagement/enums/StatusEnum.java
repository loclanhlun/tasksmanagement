package com.hbloc.taskmanagement.enums;

public enum StatusEnum {
    ACTIVE(1),
    BLOCKED(2),
    INACTIVE(3);
    private Integer value;
    private StatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

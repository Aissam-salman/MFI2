package com.app.mfi2.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The enum Role.
 */
public enum Role {
    /**
     * Client role.
     */
    CLIENT(0),
    /**
     * Admin role.
     */
    ADMIN(1),
    /**
     * Producer role.
     */
    PRODUCER(2);

    private int value;

    Role(int value) {
        this.value = value;
    }

    /**
     * From value role.
     *
     * @param value the value
     * @return the role
     */
    @JsonCreator
    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @JsonValue
    public int getValue() {
        return value;
    }

}

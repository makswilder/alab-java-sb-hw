package com.coworking.coworkingspace.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfSpace {
    OPEN_SPACE("Open Space"),
    PRIVATE("Private Room"),
    ROOM("Meeting Room");

    private final String displayName;

    TypeOfSpace(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static TypeOfSpace fromDisplayName(String displayName) {
        for (TypeOfSpace type : TypeOfSpace.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type of space: " + displayName);
    }
}

package com.coworking.coworkingspace.statusresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusResponse {
    private boolean success;
    private String message;

    public static StatusResponse success(String message) {
        return new StatusResponse(true, message);
    }

    public static StatusResponse error(String errorMessage) {
        return new StatusResponse(false, errorMessage);
    }
}

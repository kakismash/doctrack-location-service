package com.kaki.doctrack.building.exceptionHandler;

import lombok.Getter;

@Getter
public class AreaNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public AreaNotFoundException(Long areaId) {
        super("Area not found with id: " + areaId);
        errorMessage = "Area not found with id: " + areaId;
        this.errorCode = "AREA_404";
    }
}


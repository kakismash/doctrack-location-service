package com.kaki.doctrack.building.exceptionHandler;

import lombok.Getter;

@Getter
public class AreaTypeNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public AreaTypeNotFoundException(String areaTypeName, Long locationId) {
        super("AreaType not found with name: " + areaTypeName + " and locationId: " + locationId);
        errorMessage = "AreaType not found with name: " + areaTypeName + " and locationId: " + locationId;
        this.errorCode = "AREA_TYPE_404";
    }
}


package com.kaki.doctrack.building.exceptionHandler;

import lombok.Getter;

@Getter
public class AreaContainsChildrenException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public AreaContainsChildrenException(Long areaId) {
        super("Area: " + areaId + " contains children and cannot be deleted, please delete the children first or move them to another area. Alternatively, you can delete the area in batch.");
        errorMessage = "Area: " + areaId + " contains children and cannot be deleted, please delete the children first or move them to another area. Alternatively, you can delete the area in batch.";
        this.errorCode = "AREA_400";
    }
}


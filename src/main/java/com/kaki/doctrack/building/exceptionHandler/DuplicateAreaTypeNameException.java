package com.kaki.doctrack.building.exceptionHandler;

import lombok.Getter;

@Getter
public class DuplicateAreaTypeNameException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public DuplicateAreaTypeNameException(String areaTypeName) {
        super("Area type name already exists: " + areaTypeName);
        this.errorCode = "DUPLICATE_AREA_TYPE_NAME";
        this.errorMessage = "Area type name already exists: " + areaTypeName;
    }
}


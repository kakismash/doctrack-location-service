package com.kaki.doctrack.building.exceptionHandler;

import lombok.Getter;

    @Getter
    public class LocationNotFoundException extends RuntimeException {
        private final String errorCode;
        private final String errorMessage;

        public LocationNotFoundException(Long locationId) {
            super("Location not found with id: " + locationId);
            errorMessage = "Location not found with id: " + locationId;
            this.errorCode = "LOCATION_404";
        }
    }


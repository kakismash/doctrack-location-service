package com.kaki.doctrack.building.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Location}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateLocationDto(String name, String description, String phone, String address1, String address2,
                                String city, String state, String zip, String country) implements Serializable {

    public com.kaki.doctrack.building.entity.Location toEntity(Long id) {
        return new com.kaki.doctrack.building.entity.Location(id, name, description, phone, address1, address2, city, state, zip, country);
    }

}
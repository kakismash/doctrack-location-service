package com.kaki.doctrack.building.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaki.doctrack.building.entity.Location;

import java.io.Serializable;

/**
 * DTO for {@link Location}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationDto(Long id, String name, String description, String phone, String address1, String address2,
                          String city, String state, String zip, String country) implements Serializable {

    public static LocationDto fromEntity(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getDescription(), location.getPhone(),
                location.getAddress1(), location.getAddress2(), location.getCity(), location.getState(), location.getZip(),
                location.getCountry());
    }

    public Location toEntity() {
        return new Location(id, name, description, phone, address1, address2, city, state, zip, country);
    }

    public LocationDto withId(Long id) {
        return new LocationDto(id, name, description, phone, address1, address2, city, state, zip, country);
    }
}
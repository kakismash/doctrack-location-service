package com.kaki.doctrack.building.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaki.doctrack.building.entity.Location;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Location}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateLocationDto(String name, String description, String phone, String address1, String address2,
                                String city, String state, String zip, String country) implements Serializable {
  public Location toEntity() {
    Location location = new Location();

    if (StringUtils.isNotBlank(name)) {
      location.setName(name);
    } else {
      throw new IllegalArgumentException("Name is required");
    }

    if (StringUtils.isNotBlank(description)) {
      location.setDescription(description);
    }

    if (StringUtils.isNotBlank(phone)) {
      location.setPhone(phone);
    }

    if (StringUtils.isNotBlank(address1)) {
      location.setAddress1(address1);
    } else {
      throw new IllegalArgumentException("Address1 is required");
    }

    if (StringUtils.isNotBlank(address2)) {
      location.setAddress2(address2);
    }

    if (StringUtils.isNotBlank(city)) {
      location.setCity(city);
    } else {
      throw new IllegalArgumentException("City is required");
    }

    if (StringUtils.isNotBlank(state)) {
      location.setState(state);
    } else {
      throw new IllegalArgumentException("State is required");
    }

    if (StringUtils.isNotBlank(zip)) {
      location.setZip(zip);
    } else {
      throw new IllegalArgumentException("Zip is required");
    }

    if (StringUtils.isNotBlank(country)) {
      location.setCountry(country);
    } else {
      throw new IllegalArgumentException("Country is required");
    }

    return location;
  }
}
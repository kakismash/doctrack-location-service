package com.kaki.doctrack.building.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Location}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationSimpleDto(Long id, String name) implements Serializable {
}
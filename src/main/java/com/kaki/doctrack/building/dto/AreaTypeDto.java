package com.kaki.doctrack.building.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.AreaType}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AreaTypeDto(String name) implements Serializable {
  }
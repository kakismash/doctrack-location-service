package com.kaki.doctrack.building.dto.area;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Area}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AreaSimpleWithChildrenDto(Long id, String name, String type, String phone, String status, Long locationId,
                                        Long parentAreaId, List<AreaSimpleWithChildrenDto> children) implements Serializable {
}
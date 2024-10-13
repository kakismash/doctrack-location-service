package com.kaki.doctrack.building.dto.area;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Area}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AreaSimpleDto(Long id, String name, String type, String phone, String status, Long locationId,
                            Long parentAreaId) implements Serializable {
    public static AreaSimpleDto fromEntity(com.kaki.doctrack.building.entity.Area area) {
        return new AreaSimpleDto(area.getId(), area.getName(), area.getType(), area.getPhone(), area.getStatus(),
                area.getLocationId(), area.getParentAreaId() != null ? area.getParentAreaId() : null);
    }
}
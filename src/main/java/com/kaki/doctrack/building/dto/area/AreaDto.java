package com.kaki.doctrack.building.dto.area;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaki.doctrack.building.entity.Area;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Area}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AreaDto(Long id, String name, String description, String type, String phone, String address1,
                      String address2, String city, String state, String zip, String country, String status,
                      Long locationId, Long parentAreaId, List<AreaSimpleDto> children) implements Serializable {
    public static AreaDto fromEntity(Area area, List<AreaSimpleDto> children) {
        return new AreaDto(area.getId(), area.getName(), area.getDescription(), area.getType(), area.getPhone(),
                area.getAddress1(), area.getAddress2(), area.getCity(), area.getState(), area.getZip(), area.getCountry(),
                area.getStatus(), area.getLocationId(), area.getParentAreaId() != null ? area.getParentAreaId() : null,
                children);
    }
}
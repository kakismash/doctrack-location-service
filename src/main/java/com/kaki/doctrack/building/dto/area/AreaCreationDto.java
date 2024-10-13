package com.kaki.doctrack.building.dto.area;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaki.doctrack.building.entity.Area;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.doctrack.building.entity.Area}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AreaCreationDto(String name, String description, String type, String phone, String address1,
                              String address2, String city, String state, String zip, String country, String status,
                              Long parentAreaId) implements Serializable {
    public Area toEntity(Long locationId) {
        Area area = new Area();

        if (StringUtils.isNotBlank(name)) {
            area.setName(name);
        }

        if (StringUtils.isNotBlank(description)) {
            area.setDescription(description);
        }

        if (StringUtils.isNotBlank(type)) {
            area.setType(type);
        }

        if (StringUtils.isNotBlank(phone)) {
            area.setPhone(phone);
        }

        if (StringUtils.isNotBlank(address1)) {
            area.setAddress1(address1);
        }

        if (StringUtils.isNotBlank(address2)) {
            area.setAddress2(address2);
        }

        if (StringUtils.isNotBlank(city)) {
            area.setCity(city);
        }

        if (StringUtils.isNotBlank(state)) {
            area.setState(state);
        }

        if (StringUtils.isNotBlank(zip)) {
            area.setZip(zip);
        }

        if (StringUtils.isNotBlank(country)) {
            area.setCountry(country);
        }

        if (StringUtils.isNotBlank(status)) {
            area.setStatus(status);
        }

        if (parentAreaId != null) {
            area.setParentAreaId(parentAreaId);
        }

        area.setLocationId(locationId);

        return area;
    }
}
package com.kaki.doctrack.building.dto.area;

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
public record AreaUpdateDto(String name, String description, String type, String phone, String address1,
                            String address2, String city, String state, String zip, String country, String status,
                            Long parentAreaId) implements Serializable {
}
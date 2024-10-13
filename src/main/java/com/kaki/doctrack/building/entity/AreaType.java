package com.kaki.doctrack.building.entity;

import com.kaki.doctrack.building.dto.AreaTypeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@Entity(name = "area_types")  // Using JPA @Entity for PostgreSQL
@Table("area_types")  // Using R2DBC @Table for PostgreSQL
@jakarta.persistence.Table(
        name = "area_types",  // Using JPA @Table for PostgreSQL
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "location_id"})  // Unique constraint for name and location
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class AreaType {

    @jakarta.persistence.Id  // Using JPA @Id annotation
    @Id  // Using Spring Data annotation for R2DBC
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "location_id", nullable = false)
    private Long locationId;  // Store only the locationId, not the full Location object

    public AreaType(String areaTypeName, Long locationId) {
        this.name = areaTypeName;
        this.locationId = locationId;
    }
}

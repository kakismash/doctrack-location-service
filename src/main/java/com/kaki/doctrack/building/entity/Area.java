package com.kaki.doctrack.building.entity;

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
@Entity(name = "areas")  // Using JPA @Entity for PostgreSQL
@Table("areas")  // Using R2DBC @Table for PostgreSQL
@jakarta.persistence.Table(name = "areas")  // Using JPA @Table for PostgreSQL
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @jakarta.persistence.Id  // Using JPA @Id annotation
    @Id  // Using Spring Data annotation for R2DBC
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String description;

    private String type;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;

    private String status;

    // For R2DBC, relationships like @ManyToOne must be handled with references
    @Column(name = "location_id", nullable = false)
    private Long locationId;  // Reference instead of @ManyToOne

    @Column(name = "parent_area_id")
    private Long parentAreaId;  // Reference instead of @ManyToOne

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return id != null && id.equals(area.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}

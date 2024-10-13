package com.kaki.doctrack.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Entity(name = "location")  // Using JPA @Entity for PostgreSQL
@jakarta.persistence.Table(name = "location")  // Using JPA @Table for PostgreSQL
@Table("location")  // Using R2DBC @Table for PostgreSQL
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id  // Using Spring Data R2DBC @Id annotation
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;
}

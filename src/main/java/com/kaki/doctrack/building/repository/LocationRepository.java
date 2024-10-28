package com.kaki.doctrack.building.repository;

import com.kaki.doctrack.building.entity.Location;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LocationRepository extends R2dbcRepository<Location, Long>, ReactiveSortingRepository<Location, Long> {

  @Query("SELECT * FROM location WHERE " +
          "LOWER(name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(address1) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(address2) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(state) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(zip) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
          "ORDER BY name LIMIT :limit OFFSET :offset")
  Flux<Location> findBySearchTermWithPagination(
          @Param("searchTerm") String searchTerm,
          @Param("limit") int limit,
          @Param("offset") int offset);

    Flux<Location> findAllByNameContainingIgnoreCase(String name);
}
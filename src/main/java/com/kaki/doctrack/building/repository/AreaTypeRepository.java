package com.kaki.doctrack.building.repository;

import com.kaki.doctrack.building.entity.AreaType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AreaTypeRepository extends R2dbcRepository<AreaType, Long> {

    @Query("SELECT * FROM area_types WHERE location_id = :locationId AND name = :name")
    Mono<AreaType> findAreaTypeByLocation_IdAndNameEquals(Long locationId, String name);

    @Query("SELECT * FROM area_types WHERE location_id = :locationId")
    Flux<AreaType> findAllByLocation_Id(Long locationId);

    @Query("DELETE FROM area_types WHERE location_id = :locationId AND name = :areaTypeName")
    Mono<Void> deleteByLocationIdAndName(Long locationId, String areaTypeName);
}
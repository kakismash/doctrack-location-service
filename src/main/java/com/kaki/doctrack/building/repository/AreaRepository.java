package com.kaki.doctrack.building.repository;

import com.kaki.doctrack.building.entity.Area;
import com.kaki.doctrack.building.repository.custom.AreaCustomRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface AreaRepository extends R2dbcRepository<Area, Long>, AreaCustomRepository {
    Mono<Area> findByLocationIdAndId(Long locationId, Long areaId);
}

package com.kaki.doctrack.building.repository.custom;

import com.kaki.doctrack.building.dto.area.AreaSimpleDto;
import com.kaki.doctrack.building.dto.area.AreaSimpleWithChildrenDto;
import com.kaki.doctrack.building.entity.Area;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

public interface AreaCustomRepository {

    Mono<Tuple2<Flux<AreaSimpleWithChildrenDto>, Long>> findAreasAndCountBySearchTermAndLocationIdTree(
            String searchTerm,
            int limit,
            int offset,
            Long locationId);

    Mono<Tuple2<Flux<AreaSimpleDto>, Long>> findAreasAndCountBySearchTermAndLocationIdFlat(
            String searchTerm,
            int limit,
            int offset,
            Long locationId);

    Mono<Tuple2<Area, List<AreaSimpleDto>>> findByLocationIdAndIdAndChildren(Long locationId, Long areaId);
}

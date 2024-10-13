package com.kaki.doctrack.building.repository.custom;

import com.kaki.doctrack.building.dto.area.AreaDto;
import com.kaki.doctrack.building.dto.area.AreaSimpleDto;
import com.kaki.doctrack.building.dto.area.AreaSimpleWithChildrenDto;
import com.kaki.doctrack.building.entity.Area;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@RequiredArgsConstructor
public class AreaCustomRepositoryImpl implements AreaCustomRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<Tuple2<Flux<AreaSimpleWithChildrenDto>, Long>> findAreasAndCountBySearchTermAndLocationIdTree(
            String searchTerm,
            int limit,
            int offset,
            Long locationId) {

        return findAreasAndCountBySearchTermAndLocationIdFlat(searchTerm, limit, offset, locationId)
                .flatMap(tuple -> {
                    Flux<AreaSimpleDto> areasFlux = tuple.getT1();

                    Long totalElements = tuple.getT2();

                    return Mono.just(Tuples.of(buildTreeFromFlat(areasFlux, null), totalElements));
                });

    }

    private Flux<AreaSimpleWithChildrenDto> buildTreeFromFlat(Flux<AreaSimpleDto> flatAreas, Long parentAreaId) {
        return flatAreas.filter(area -> area.parentAreaId().equals(parentAreaId))
                .flatMap(area -> {
                    Flux<AreaSimpleWithChildrenDto> children = buildTreeFromFlat(flatAreas, area.id());
                    return Flux.just(new AreaSimpleWithChildrenDto(area.id(),
                            area.name(),
                            area.type(),
                            area.phone(),
                            area.status(),
                            area.locationId(),
                            area.parentAreaId(),
                            children.collectList().block()
                            ));
                });
    }

    @Override
    public Mono<Tuple2<Flux<AreaSimpleDto>, Long>> findAreasAndCountBySearchTermAndLocationIdFlat(
            String searchTerm,
            int limit,
            int offset,
            Long locationId) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM areas WHERE location_id = :locationId");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryBuilder.append(" AND (LOWER(name) LIKE :searchTerm OR LOWER(description) LIKE :searchTerm)");
        }

        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        String query = queryBuilder.toString();

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query)
                .bind("locationId", locationId)
                .bind("limit", limit)
                .bind("offset", offset);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            spec = spec.bind("searchTerm", likePattern);
        }

        Flux<AreaSimpleDto> areas = spec.map((row, metadata) -> {
            return new AreaSimpleDto(
                    row.get("id", Long.class),
                    row.get("name", String.class),
                    row.get("type", String.class),
                    row.get("phone", String.class),
                    row.get("status", String.class),
                    locationId,
                    row.get("parent_area_id", Long.class));
        }).all();

        String countQuery = "SELECT COUNT(*) AS total FROM areas WHERE location_id = :locationId";

        if (searchTerm != null && !searchTerm.isEmpty()) {
            countQuery += " AND (LOWER(name) LIKE :searchTerm OR LOWER(description) LIKE :searchTerm)";
        }

        DatabaseClient.GenericExecuteSpec countSpec = databaseClient.sql(countQuery)
                .bind("locationId", locationId);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            countSpec = countSpec.bind("searchTerm", likePattern);
        }

        Mono<Long> totalCount = countSpec.map((row, metadata) -> row.get("total", Long.class)).one();

        return totalCount.map(count -> Tuples.of(areas, count));
    }

    @Override
    public Mono<Tuple2<Area, List<AreaSimpleDto>>> findByLocationIdAndIdAndChildren(Long locationId, Long areaId) {
        // First, retrieve the main area
        String mainAreaQuery = "SELECT * FROM areas WHERE id = :areaId AND location_id = :locationId";

        // Query to retrieve the IDs of the child areas
        String childAreasQuery = "SELECT id, name, description, status, type FROM areas WHERE parent_area_id = :areaId AND location_id = :locationId";

        // Retrieve the main area
        Mono<Area> mainAreaMono = databaseClient.sql(mainAreaQuery)
                .bind("areaId", areaId)
                .bind("locationId", locationId)
                .map((row, metadata) -> {
                    Area area = new Area();
                    area.setId(row.get("id", Long.class));
                    area.setName(row.get("name", String.class));
                    area.setDescription(row.get("description", String.class));
                    area.setType(row.get("type", String.class));
                    area.setPhone(row.get("phone", String.class));
                    area.setAddress1(row.get("address1", String.class));
                    area.setAddress2(row.get("address2", String.class));
                    area.setCity(row.get("city", String.class));
                    area.setState(row.get("state", String.class));
                    area.setZip(row.get("zip", String.class));
                    area.setCountry(row.get("country", String.class));
                    area.setStatus(row.get("status", String.class));
                    area.setLocationId(row.get("location_id", Long.class));
                    area.setParentAreaId(row.get("parent_area_id", Long.class));
                    return area;
                }).one();

        // Retrieve the child area IDs
        Flux<AreaSimpleDto> childAreaIdsFlux = databaseClient.sql(childAreasQuery)
                .bind("areaId", areaId)
                .bind("locationId", locationId)
                .map((row, metadata) -> new AreaSimpleDto(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("type", String.class),
                        row.get("phone", String.class),
                        row.get("status", String.class),
                        locationId,
                        areaId))
                .all();

        // Combine the main area and its child area IDs into a Tuple2
        return mainAreaMono.flatMap(mainArea ->
                childAreaIdsFlux.collectList().map(childAreaIds ->
                        Tuples.of(mainArea, childAreaIds)  // Create a tuple with the main area and the list of child IDs
                )
        );
    }


}
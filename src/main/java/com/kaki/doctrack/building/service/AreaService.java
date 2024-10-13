package com.kaki.doctrack.building.service;

import com.kaki.doctrack.building.dto.area.*;
import com.kaki.doctrack.building.entity.Area;
import com.kaki.doctrack.building.exceptionHandler.AreaContainsChildrenException;
import com.kaki.doctrack.building.exceptionHandler.AreaNotFoundException;
import com.kaki.doctrack.building.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final Logger logger = LoggerFactory.getLogger(AreaService.class);

    private final AreaRepository areaRepository;

    public Mono<Page<AreaSimpleWithChildrenDto>> getAreasTree(
            String searchTerm,
            int page,
            int size,
            Long locationId) {

        int offset = page * size;
        int limit = size;

        return areaRepository.findAreasAndCountBySearchTermAndLocationIdTree(searchTerm, limit, offset, locationId)
                .flatMap(tuple -> {
                    Flux<AreaSimpleWithChildrenDto> areasFlux = tuple.getT1();

                    Long totalElements = tuple.getT2();

                    Mono<List<AreaSimpleWithChildrenDto>> areasMono = areasFlux.collectList();

                    return areasMono.map(areas -> new PageImpl<>(areas, PageRequest.of(page, size), totalElements));
                });
    }

    public Mono<Page<AreaSimpleDto>> getAreasFlat(
            String searchTerm,
            int page,
            int size,
            Long locationId) {

        int offset = page * size;
        int limit = size;

        return areaRepository.findAreasAndCountBySearchTermAndLocationIdFlat(searchTerm, limit, offset, locationId)
                .flatMap(tuple -> {
                    Flux<AreaSimpleDto> areasFlux = tuple.getT1();

                    Long totalElements = tuple.getT2();

                    Mono<List<AreaSimpleDto>> areasMono = areasFlux.collectList();

                    return areasMono.map(areas -> new PageImpl<>(areas, PageRequest.of(page, size), totalElements));
                });
    }

    public Mono<AreaDto> findAreaByLocationAndId(Long locationId, Long areaId) {
        return areaRepository.findByLocationIdAndIdAndChildren(locationId, areaId)
                .switchIfEmpty(Mono.error(new AreaNotFoundException(areaId)))
                .flatMap(tuple -> {
                    Area area = tuple.getT1();
                    List<AreaSimpleDto> children = tuple.getT2();

                    AreaDto areaDto = AreaDto.fromEntity(area, children);

                    return Mono.just(areaDto);
                });
    }

    public Mono<AreaDto> createArea(Long locationId, AreaCreationDto areaDto) {
        return areaRepository.save(areaDto.toEntity(locationId)).map(area -> AreaDto.fromEntity(area, List.of()));
    }

    public Mono<AreaDto> updateArea(Long locationId, Long areaId, AreaUpdateDto areaDto) {
        return areaRepository.findByLocationIdAndIdAndChildren(locationId, areaId)
                .switchIfEmpty(Mono.error(new AreaNotFoundException(areaId)))
                .flatMap(tuple -> {
                    Area area = tuple.getT1();
                    List<AreaSimpleDto> children = tuple.getT2();

                    if (StringUtils.isNotBlank(areaDto.name())) {
                        area.setName(areaDto.name());
                    }

                    if (StringUtils.isNotBlank(areaDto.description())) {
                        area.setDescription(areaDto.description());
                    }

                    if (StringUtils.isNotBlank(areaDto.type())) {
                        area.setType(areaDto.type());
                    }

                    if (StringUtils.isNotBlank(areaDto.phone())) {
                        area.setPhone(areaDto.phone());
                    }

                    if (StringUtils.isNotBlank(areaDto.address1())) {
                        area.setAddress1(areaDto.address1());
                    }

                    if (StringUtils.isNotBlank(areaDto.address2())) {
                        area.setAddress2(areaDto.address2());
                    }

                    if (StringUtils.isNotBlank(areaDto.city())) {
                        area.setCity(areaDto.city());
                    }

                    if (StringUtils.isNotBlank(areaDto.state())) {
                        area.setState(areaDto.state());
                    }

                    if (StringUtils.isNotBlank(areaDto.zip())) {
                        area.setZip(areaDto.zip());
                    }

                    if (StringUtils.isNotBlank(areaDto.country())) {
                        area.setCountry(areaDto.country());
                    }

                    if (StringUtils.isNotBlank(areaDto.status())) {
                        area.setStatus(areaDto.status());
                    }

                    if (areaDto.parentAreaId() != null) {
                        area.setParentAreaId(areaDto.parentAreaId());
                    }

                    return areaRepository.save(area).map(updatedArea -> AreaDto.fromEntity(updatedArea, children));
                });
    }

    public Mono<Void> deleteArea(Long locationId, Long areaId) {
        return areaRepository.findByLocationIdAndIdAndChildren(locationId, areaId)
                .switchIfEmpty(Mono.error(new AreaNotFoundException(areaId)))
                .flatMap(tuple -> {
                    Area area = tuple.getT1();
                    List<AreaSimpleDto> children = tuple.getT2();

                    if (!children.isEmpty()) {
                        return Mono.error(new AreaContainsChildrenException(areaId));
                    }

                    return areaRepository.delete(area);
                });
    }
}

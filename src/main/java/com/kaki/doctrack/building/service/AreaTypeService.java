package com.kaki.doctrack.building.service;

import com.kaki.doctrack.building.dto.AreaTypeDto;
import com.kaki.doctrack.building.entity.AreaType;
import com.kaki.doctrack.building.exceptionHandler.AreaTypeNotFoundException;
import com.kaki.doctrack.building.exceptionHandler.DuplicateAreaTypeNameException;
import com.kaki.doctrack.building.repository.AreaTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AreaTypeService {
    private final AreaTypeRepository areaTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(AreaTypeService.class);

    public Mono<Void> deleteAreaType(Long locationId, String areaTypeName) {
        logger.info("deleteAreaType");
        return areaTypeRepository.deleteByLocationIdAndName(locationId, areaTypeName);
    }

    public Mono<?> addAreaType(Long locationId, AreaTypeDto areaTypeDto) {
        logger.info("addAreaType");

        return areaTypeRepository.findAreaTypeByLocation_IdAndNameEquals(locationId, areaTypeDto.name())
                .flatMap(existingAreaType -> {
                    // If an AreaType with the same name exists, throw the exception as an error in Mono
                    return Mono.error(new DuplicateAreaTypeNameException(areaTypeDto.name()));
                })
                .switchIfEmpty(
                        // If no duplicate found, save the new AreaType and return it wrapped in AreaTypeDto
                        areaTypeRepository.save(
                                new AreaType(areaTypeToUpperCaseAndReplaceSpaceWith__(
                                        areaTypeDto.name()),
                                        locationId
                                ))
                                .map(savedAreaType -> new AreaTypeDto(savedAreaType.getName()))
                                .flatMap(aT -> Mono.just(aT.name()))
                );
    }

    private String areaTypeToUpperCaseAndReplaceSpaceWith__(String areaTypeName) {
        return areaTypeName.toUpperCase().replace(" ", "__");
    }

    public Flux<String> getAreaTypes(Long locationId) {
        logger.info("getAreaTypes");
        return areaTypeRepository.findAllByLocation_Id(locationId)
                .map(AreaType::getName);
    }
}

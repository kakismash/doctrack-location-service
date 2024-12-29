package com.kaki.doctrack.building.service;

import com.kaki.doctrack.building.dto.location.CreateLocationDto;
import com.kaki.doctrack.building.dto.location.LocationDto;
import com.kaki.doctrack.building.dto.location.LocationSimpleDto;
import com.kaki.doctrack.building.entity.Location;
import com.kaki.doctrack.building.exceptionHandler.LocationNotFoundException;
import com.kaki.doctrack.building.repository.LocationRepository;
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

@Service
@RequiredArgsConstructor
public class LocationService {
    private final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    public Mono<Page<LocationDto>> findLocationBySearchTerm(String searchTerm, int page, int size) {
        return locationRepository.findBySearchTermWithPagination(searchTerm, size, page * size)
                .collectList()
                .map(buildings -> buildings.stream().map(LocationDto::fromEntity).toList())
                .flatMap(buildingDtos -> locationRepository.count()
                        .map(count -> new PageImpl<>(buildingDtos, PageRequest.of(page, size), count)));
    }

    public Mono<LocationDto> findLocationById(Long id) {
        return locationRepository.findById(id)
                .map(LocationDto::fromEntity)
                .doOnSuccess(location -> logger.info("Location found: {}", location))
                .doOnError(e -> logger.error("Error finding location", e));
    }

    public Mono<LocationDto> createLocation(CreateLocationDto newLocationDto) {
        return locationRepository.save(newLocationDto.toEntity())
                .doOnNext(location -> logger.info("Location created: {}", location))  // Log after the entity is saved
                .map(LocationDto::fromEntity)  // Convert the saved entity to a DTO
                .doOnError(e -> logger.error("Error creating location", e));  // Handle errors and log them
    }

    public Mono<LocationDto> updateLocation(Location locationToUpdate) {
        return locationRepository.findById(locationToUpdate.getId())
                .switchIfEmpty(Mono.error(new LocationNotFoundException(locationToUpdate.getId())))
                .flatMap(inDb -> {
            if (StringUtils.isNotBlank(locationToUpdate.getName())) {
                inDb.setName(locationToUpdate.getName());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getDescription())) {
                inDb.setDescription(locationToUpdate.getDescription());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getPhone())) {
                inDb.setPhone(locationToUpdate.getPhone());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getAddress1())) {
                inDb.setAddress1(locationToUpdate.getAddress1());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getAddress2())) {
                inDb.setAddress2(locationToUpdate.getAddress2());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getCity())) {
                inDb.setCity(locationToUpdate.getCity());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getState())) {
                inDb.setState(locationToUpdate.getState());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getZip())) {
                inDb.setZip(locationToUpdate.getZip());
            }

            if (StringUtils.isNotBlank(locationToUpdate.getCountry())) {
                inDb.setCountry(locationToUpdate.getCountry());
            }

            return locationRepository.save(inDb);

        })
        .map(LocationDto::fromEntity)
        .doOnSuccess(location -> {
            logger.info("Location updated: {}", location);
        })
        .doOnError(e -> logger.error("Error updating location", e));
    }

    public Mono<Void> deleteLocation(Long id) {
        return locationRepository.deleteById(id)
                .doOnSuccess(location -> logger.info("Location deleted: {}", id))
                .doOnError(e -> logger.error("Error deleting location", e));
    }

    public Flux<LocationSimpleDto> findAllWithSearchTerm(String searchTerm) {
        return locationRepository.findAllByNameContainingIgnoreCase(searchTerm)
                .map(location -> new LocationSimpleDto(location.getId(), location.getName()));
    }

    public Flux<LocationDto> findLocationsByIds(Flux<Long> locationIds) {
        return locationRepository.findAllById(locationIds)
                .map(LocationDto::fromEntity);
    }
}

package com.kaki.doctrack.building.controller;

import com.kaki.doctrack.building.dto.location.CreateLocationDto;
import com.kaki.doctrack.building.dto.location.LocationDto;
import com.kaki.doctrack.building.dto.location.UpdateLocationDto;
import com.kaki.doctrack.building.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;

    @GetMapping()
    public Mono<ResponseEntity<Page<LocationDto>>> getLocations(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @RequestParam(value = "page", defaultValue = "0") int page,  // Default page = 0
            @RequestParam(value = "size", defaultValue = "10") int size,  // Default size = 10
            @RequestParam(value = "search", required = false, defaultValue = "") String searchTerm) {

        logger.info("getLocations");

        if (role.equals("SUPERADMIN") || role.equals("ADMIN")) {
            return locationService.findLocationBySearchTerm(searchTerm, page, size)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<LocationDto>> getLocationById(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id) {

        logger.info("getLocationById");

        if (role.equals("SUPERADMIN") || role.equals("ADMIN")) {
            return locationService.findLocationById(id)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @PostMapping()
    public Mono<ResponseEntity<LocationDto>> createLocation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @RequestBody CreateLocationDto newLocationDto) {

        logger.info("createLocation");

        if (role.equals("SUPERADMIN") || role.equals("ADMIN")) {
            return locationService.createLocation(newLocationDto)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<LocationDto>> updateLocation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id,
            @RequestBody UpdateLocationDto updateLocationDto) {

        logger.info("updateLocation");

        if (role.equals("SUPERADMIN") || role.equals("ADMIN")) {
            return locationService.updateLocation(updateLocationDto.toEntity(id))
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLocation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id) {

        logger.info("deleteLocation");

        if (role.equals("SUPERADMIN") || role.equals("ADMIN")) {
            return locationService.deleteLocation(id)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

}

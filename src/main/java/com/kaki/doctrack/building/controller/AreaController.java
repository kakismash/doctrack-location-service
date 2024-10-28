package com.kaki.doctrack.building.controller;

import com.kaki.doctrack.building.dto.area.AreaCreationDto;
import com.kaki.doctrack.building.dto.area.AreaDto;
import com.kaki.doctrack.building.dto.area.AreaSimpleDto;
import com.kaki.doctrack.building.dto.area.AreaUpdateDto;
import com.kaki.doctrack.building.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/locations/{locationId}/areas")
@RequiredArgsConstructor
public class AreaController {
    private final Logger logger = LoggerFactory.getLogger(AreaController.class);

    private final AreaService areaService;

    @GetMapping
    public Mono<ResponseEntity<Page<AreaSimpleDto>>> getAreasByLocation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @RequestParam(value = "page", defaultValue = "0") int page,  // Default page = 0
            @RequestParam(value = "size", defaultValue = "10") int size,  // Default size = 10
            @RequestParam(value = "search", required = false, defaultValue = "") String searchTerm) {

        logger.info("getAreas");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaService.getAreasFlat(searchTerm, page, size, locationId)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @GetMapping("/{areaId}")
    public Mono<ResponseEntity<AreaDto>> getAreaByLocation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @PathVariable("areaId") Long areaId) {

        logger.info("getAreaByLocation");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaService.findAreaByLocationAndId(locationId, areaId)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @PostMapping
    public Mono<ResponseEntity<AreaDto>> createArea(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @RequestBody AreaCreationDto areaDto) {

        logger.info("createArea");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaService.createArea(locationId, areaDto)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @PutMapping("/{areaId}")
    public Mono<ResponseEntity<AreaDto>> updateArea(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @PathVariable("areaId") Long areaId,
            @RequestBody AreaUpdateDto areaDto) {

        logger.info("updateArea");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaService.updateArea(locationId, areaId, areaDto)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @DeleteMapping("/{areaId}")
    public Mono<ResponseEntity<Void>> deleteArea(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @PathVariable("areaId") Long areaId) {

        logger.info("deleteArea");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaService.deleteArea(locationId, areaId)
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

}

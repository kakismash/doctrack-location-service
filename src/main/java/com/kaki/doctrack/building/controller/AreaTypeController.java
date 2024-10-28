package com.kaki.doctrack.building.controller;

import com.kaki.doctrack.building.dto.AreaTypeDto;
import com.kaki.doctrack.building.service.AreaTypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations/{locationId}/areas/types")
@RequiredArgsConstructor
public class AreaTypeController {
    private final Logger logger = LoggerFactory.getLogger(AreaTypeController.class);

    private final AreaTypeService areaTypeService;

    @GetMapping
    public Mono<ResponseEntity<Mono<List<String>>>> getAreaTypes(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId) {

        logger.info("getAreaTypes");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            // Return a list of strings as a Mono<List<String>>
            return Mono.just(
                    ResponseEntity.ok(
                            areaTypeService.getAreaTypes(locationId)  // This returns a Flux<String>
                                    .collectList()  // Convert Flux<String> to Mono<List<String>>
                    )
            );
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @PostMapping("/{areaTypeName}")
    public Mono<ResponseEntity<?>> addAreaType(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @PathVariable("areaTypeName") String areaTypeName) {

        logger.info("addAreaType");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaTypeService.addAreaType(locationId, new AreaTypeDto(areaTypeName))
                    .map(ResponseEntity::ok);
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

    @DeleteMapping("/{areaTypeName}")
    public Mono<ResponseEntity<?>> deleteAreaType(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable("locationId") Long locationId,
            @PathVariable("areaTypeName") String areaTypeName) {

        logger.info("deleteAreaType");

        if ("SUPER_ADMIN".equals(role) || "ADMIN".equals(role)) {
            return areaTypeService.deleteAreaType(locationId, areaTypeName)
                    .then(Mono.just(ResponseEntity.ok().build()));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
    }

}

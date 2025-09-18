package com.titan.tune.Controller;

import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Dto.Response.AlbumResponse;
import com.titan.tune.Service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/albums")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Albums", description = "API for managing music albums")
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService service;

    @PostMapping(path="/create")
    @Operation(summary = "Create a new album", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Album created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "401", description = "Unauthorized - authentication required")
    public ResponseEntity<AlbumResponse> createAlbum(@RequestBody AlbumRequest request) {
        log.info("Creating new album: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping(path="/get/{trackingId}")
    @Operation(summary = "Get album by ID")
    @ApiResponse(responseCode = "200", description = "Album retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Album not found")
    public ResponseEntity<AlbumResponse> getAlbum(@PathVariable UUID trackingId) {
        log.info("Fetching album with ID: {}", trackingId);
        var response = this.service.getAlbum(trackingId) ;
        return  new ResponseEntity<>(response  , HttpStatus.OK);
    }

    @GetMapping(path="/all")
    @Operation(summary = "Get all albums")
    @ApiResponse(responseCode = "200", description = "Albums retrieved successfully")
    public ResponseEntity<?> getAllAlbums() {
        log.info("Fetching all albums");
        var response = this.service.listeAlbums() ;
        return new  ResponseEntity<>(response  , HttpStatus.OK);
    }

    @PutMapping(path="/update/{trackingId}")
    @Operation(summary = "Update an album", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Album updated successfully")
    @ApiResponse(responseCode = "404", description = "Album not found")
    public ResponseEntity<AlbumResponse> updateAlbum(@PathVariable UUID trackingId, @RequestBody AlbumRequest request) {
        log.info("Updating album with ID: {}", trackingId);
        return ResponseEntity.ok(service.update(trackingId, request));
    }

    @DeleteMapping(path="/delete/{trackingId}")
    @Operation(summary = "Delete an album", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Album deleted successfully")
    @ApiResponse(responseCode = "404", description = "Album not found")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID trackingId) {
        log.info("Deleting album with ID: {}", trackingId);
        service.delete(trackingId);
        return ResponseEntity.noContent().build();
    }
}
package com.titan.tune.Controller;

import com.titan.tune.Dto.Request.CategorieRequest;
import com.titan.tune.Dto.Response.CategorieResponse;
import com.titan.tune.Service.ImplService.CategorieServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Categories", description = "API for managing music categories")
@Slf4j
public class CategorieController {

    private final CategorieServiceImpl service;

    public CategorieController(CategorieServiceImpl service) {
        this.service = service;
    }

    @PostMapping(path="/create")
    @Operation(summary = "Create a new category", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "409", description = "Category already exists")
    public ResponseEntity<CategorieResponse> createCategory(@RequestBody CategorieRequest request) {
        log.info("Creating new category: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategorie(request));
    }

    @PutMapping(path="/update/{trackingId}")
    @Operation(summary = "Update a category", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<CategorieResponse> updateCategory(
            @PathVariable UUID trackingId, @RequestBody CategorieRequest request) {
        log.info("Updating category with ID: {}", trackingId);
        return ResponseEntity.ok(service.updateCategorie(trackingId, request));
    }

    @GetMapping(path="/get/{trackingId}")
    @Operation(summary = "Get category by ID")
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<CategorieResponse> getCategoryById(@PathVariable UUID trackingId) {
        log.info("Fetching category with ID: {}", trackingId);
        return ResponseEntity.ok(service.findByTrackingId(trackingId));
    }

    @GetMapping(path="/all")
    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    public ResponseEntity<?> getAllCategories() {
        log.info("Fetching all categories");
        return ResponseEntity.ok(service.listCategories());
    }

    @DeleteMapping(path="/delete/{trackingId}")
    @Operation(summary = "Delete a category", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID trackingId) {
        log.info("Deleting category with ID: {}", trackingId);
        service.deleteByTrackingId(trackingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path="/getByName/{name}")
    @Operation(summary = "Get category by name")
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        log.info("Fetching category with name: {}", name);
        return ResponseEntity.ok(service.findByName(name));
    }
}
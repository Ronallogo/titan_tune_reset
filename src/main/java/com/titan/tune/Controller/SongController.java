package com.titan.tune.Controller;


import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/song")
@CrossOrigin("*")
@Slf4j
@Tag(name = "Gestion des chansons", description = "CRUD et recherches liées aux chansons")
public class SongController {

    private final SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    @Operation(
            summary = "Créer une chanson",
            description = "Permet d'enregistrer une nouvelle chanson"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de requête invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Parameter(description = "Données de la chanson", required = true)
            @RequestBody SongRequest request
    ) {
        var response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Récupérer une chanson",
            description = "Retourne une chanson à partir de son UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/get/{trackingIdSong}")
    public ResponseEntity<?> get(
            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.getSong(trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Lister toutes les chansons",
            description = "Retourne toutes les chansons disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        var response = this.service.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Lister toutes les chansons d'un artiste",
            description = "Retourne toutes les chansons créées par un artiste donné"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/getAllForOne/{trackingIdArtiste}")
    public ResponseEntity<?> getAllForOne(
            @Parameter(description = "UUID de l'artiste", required = true)
            @PathVariable UUID trackingIdArtiste
    ) {
        var response = this.service.getSongsByArtiste(trackingIdArtiste);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Mettre à jour une chanson",
            description = "Met à jour les informations d'une chanson existante à l’aide de son UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de requête invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PutMapping("/update/{trackingIdSong}")
    public ResponseEntity<?> update(
            @Parameter(description = "UUID de la chanson à modifier", required = true)
            @PathVariable UUID trackingIdSong,

            @Parameter(description = "Nouvelles données de la chanson", required = true)
            @RequestBody SongRequest request
    ) {
        var response = this.service.update(trackingIdSong, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Supprimer une chanson",
            description = "Supprime une chanson de la base à partir de son UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson supprimée avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @DeleteMapping("/delete/{trackingIdSong}")
    public ResponseEntity<?> delete(
            @Parameter(description = "UUID de la chanson à supprimer", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        this.service.delete(trackingIdSong);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Rechercher des chansons par catégorie",
            description = "Retourne les chansons appartenant à une catégorie spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Nom de catégorie invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/getByCategorieName/{category_name}")
    public ResponseEntity<?> getByCategorieName(
            @Parameter(description = "Nom de la catégorie", required = true)
            @PathVariable String category_name
    ) {
        var response = this.service.findByNomCategorieContaining(category_name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

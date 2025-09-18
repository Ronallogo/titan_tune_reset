package com.titan.tune.Controller;


import com.titan.tune.Dto.Request.FavorisRequest;
import com.titan.tune.Dto.Response.FavorisResponse;
import com.titan.tune.Service.FavorisService;
import com.titan.tune.Service.ImplService.FavorisServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoris")
@Tag(name = "favoris", description = "description de favoris")
@CrossOrigin(origins = "*")
public class  FavorisController {
    private final FavorisServiceImpl favorisService;

    public FavorisController(FavorisServiceImpl favorisService) {
        this.favorisService = favorisService;
    }

    @Operation(
            summary = "Créer un favoris",
            description = "Crée un nouveau favoris dans la liste des favoris"
    )
    @ApiResponse(
            responseCode = "201"
            ,description = "Favoris créé avec succès",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FavorisResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")

    @PostMapping
    public ResponseEntity<FavorisResponse> createFavoris(@RequestBody FavorisRequest favorisRequest) {
        if(favorisRequest == null)
            return ResponseEntity.badRequest().build();
        try {
            FavorisResponse favorisResponse = favorisService.addFavoris(favorisRequest);
            return new ResponseEntity<>(favorisResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Obtenir un favoris par son trackingId",
            description = "Récupère un favoris spécifique en utilisant son trackingId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Favoris récupéré avec succès",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FavorisResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "404", description = "Favoris non trouvé")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")

    @GetMapping("/get/{trackingId}")
    public ResponseEntity<FavorisResponse> getFavorisByTrackingId(@PathVariable UUID trackingId){
        if(trackingId == null)
            return ResponseEntity.badRequest().build();
        try {
            FavorisResponse favorisResponse = favorisService.getFavorisByTrackingId(trackingId);
            return new ResponseEntity<>(favorisResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Obtenir tous les favoris",
            description = "Récupère la liste de tous les favoris"
    )
    @ApiResponse(responseCode = "201", description = "Liste des favoris récupérée avec succès",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FavorisResponse.class)
            )
    )
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")

    @GetMapping(path = "/all")
    public ResponseEntity<List<FavorisResponse>> getAllFavoris() {
        try {
            List<FavorisResponse> favorisResponse = favorisService.getAllFavoris();
            return new ResponseEntity<>(favorisResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Supprimer un favoris par son trackingId",
            description = "Supprime un favoris spécifique en utilisant son trackingId"
    )
    @ApiResponse(responseCode = "201", description = "Liste des favoris récupérée avec succès",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FavorisResponse.class)
            )
    )

    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "404", description = "Favoris non trouvé")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")

    @DeleteMapping("/delete/{trackingId}")
    public ResponseEntity<Void> deleteFavoris(@PathVariable UUID trackingId) {
        if(trackingId == null)
            return ResponseEntity.badRequest().build();
        try {
            favorisService.deleteFavoris(trackingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.titan.tune.Controller;


import com.titan.tune.Service.ImplService.SongEcouterServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/songEcouter")
@Tag(name = "Chansons écoutées", description = "Gestion des écoutes de chansons par les utilisateurs")
public class SongEcouterController {

    private final SongEcouterServiceImpl service;

    public SongEcouterController(SongEcouterServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "Enregistrer une écoute",
            description = "Enregistre qu'un utilisateur a écouté une chanson"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Écoute enregistrée avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/register/{trackingIdUser}/{trackingIdSong}")
    public ResponseEntity<?> register(
            @Parameter(description = "UUID de l'utilisateur", required = true)
            @PathVariable UUID trackingIdUser,

            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.create(trackingIdUser, trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Lister toutes les écoutes",
            description = "Retourne la liste complète de toutes les écoutes enregistrées"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var response = this.service.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Lister les écoutes d’un utilisateur",
            description = "Retourne toutes les chansons écoutées par un utilisateur donné"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide")
    })
    @GetMapping("/allForOne/{trackingIdUser}")
    public ResponseEntity<?> allForOne(
            @Parameter(description = "UUID de l'utilisateur", required = true)
            @PathVariable UUID trackingIdUser
    ) {
        var response = this.service.getAllForOne(trackingIdUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Rechercher une chanson écoutée par titre",
            description = "Effectue une recherche sur les chansons écoutées en fonction de leur titre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats trouvés"),
            @ApiResponse(responseCode = "400", description = "Titre invalide")
    })
    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchByTitle(
            @Parameter(description = "Titre de la chanson (partiel ou complet)", required = true)
            @PathVariable String title
    ) {
        var response = this.service.searchByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

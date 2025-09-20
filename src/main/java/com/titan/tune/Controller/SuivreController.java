package com.titan.tune.Controller;


import com.titan.tune.Service.ImplService.SuivreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/suivre")
@Tag(name = "Suivi des artistes", description = "Endpoints pour suivre et ne plus suivre les artistes")
public class SuivreController {

    private final SuivreServiceImpl service;

    public SuivreController(SuivreServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "Suivre un artiste",
            description = "Permet à un utilisateur de suivre un artiste"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artiste suivi avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/follow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<?> follow(
            @Parameter(description = "UUID de l'utilisateur qui suit", required = true)
            @PathVariable UUID trackingIdFollower,

            @Parameter(description = "UUID de l'artiste à suivre", required = true)
            @PathVariable UUID trackingIdArtiste
    ) {
        var response = this.service.follow(trackingIdFollower, trackingIdArtiste);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Ne plus suivre un artiste",
            description = "Permet à un utilisateur d'arrêter de suivre un artiste"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artiste retiré des suivis"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/unfollow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<?> unfollow(
            @Parameter(description = "UUID de l'utilisateur", required = true)
            @PathVariable UUID trackingIdFollower,

            @Parameter(description = "UUID de l'artiste à ne plus suivre", required = true)
            @PathVariable UUID trackingIdArtiste
    ) {
        this.service.unfollow(trackingIdFollower, trackingIdArtiste);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Nombre de followers d'un artiste",
            description = "Retourne le nombre total de followers d'un artiste"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping("/nbrFollowers/{trackingIdArtiste}")
    public ResponseEntity<?> nbrFollowersForOneArtiste(
            @Parameter(description = "UUID de l'artiste", required = true)
            @PathVariable UUID trackingIdArtiste
    ) {
        var value = this.service.nbrFollowers(trackingIdArtiste);
        Map<String, Object> response = new HashMap<>();
        response.put("nombre_followers", value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Nombre d'artistes suivis par un utilisateur",
            description = "Retourne le nombre d'artistes qu'un utilisateur suit"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping("/nbrFollowings/{trackingIdClient}")
    public ResponseEntity<?> nbrFollowingForOneUser(
            @Parameter(description = "UUID du client/utilisateur", required = true)
            @PathVariable UUID trackingIdClient
    ) {
        var value = this.service.nbrFollowing(trackingIdClient);
        Map<String, Object> response = new HashMap<>();
        response.put("nombre_following", value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Liste des followers d'un artiste",
            description = "Retourne la liste complète des utilisateurs qui suivent un artiste donné"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping("/followers/{trackingIdArtiste}")
    public ResponseEntity<?> allFollowerArtiste(
            @Parameter(description = "UUID de l'artiste", required = true)
            @PathVariable UUID trackingIdArtiste
    ) {
        var response = this.service.followers(trackingIdArtiste);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Liste des artistes suivis par un utilisateur",
            description = "Retourne la liste des artistes qu’un utilisateur suit"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping("/followings/{trackingIdClient}")
    public ResponseEntity<?> allFollowing(
            @Parameter(description = "UUID du client", required = true)
            @PathVariable UUID trackingIdClient
    ) {
        var response = this.service.following(trackingIdClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

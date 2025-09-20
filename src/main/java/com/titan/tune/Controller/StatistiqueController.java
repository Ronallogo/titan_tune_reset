package com.titan.tune.Controller;

import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Service.ImplService.StatistiqueServiceImpl;
import com.titan.tune.Service.StatistiqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path="/stats")
@Tag(name = "Statistiques", description = "Endpoints pour les statistiques d'écoute, de likes, de playlists, etc.")
public class StatistiqueController {

    private final StatistiqueServiceImpl service;

    public StatistiqueController(StatistiqueServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "Nombre d'utilisateurs activés",
            description = "Retourne le nombre total d'utilisateurs ayant un compte activé"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(path="/nbrUserEnabled")
    public ResponseEntity<?> nbruserEnabled() {
        var response = this.service.nbrUserEnabled();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Nombre de likes pour une chanson",
            description = "Retourne le nombre total de likes pour une chanson spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide")
    })
    @GetMapping(path="/nbrLikeForOneSong/{trackingIdSong}")
    public ResponseEntity<?> nbrLikeForOneSong(
            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.nbrLikeForOneSong(trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Nombre total d'écoutes d'une chanson",
            description = "Retourne le nombre total d'écoutes pour une chanson donnée"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide")
    })
    @GetMapping("/nbrTotalEcoute/{trackingIdSong}")
    public ResponseEntity<?> nbrTotalEcoute(
            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.nbrTotalEcouteForOneSong(trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Artiste le plus écouté",
            description = "Retourne l'artiste ayant le plus grand nombre d'écoutes"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(path="/artistMostListen")
    public ResponseEntity<?> artistMostListen() {
        var response = this.service.artistMostListen();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Chanson la plus écoutée",
            description = "Retourne la chanson ayant le plus d'écoutes"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping("/songMostListen")
    public ResponseEntity<?> songMostListen() {
        var response = this.service.songMostListen();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Chanson la plus likée",
            description = "Retourne la chanson ayant reçu le plus de likes"
    )
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(path="/songWithMostLike")
    public ResponseEntity<?> songWithMostLike() {
        var response = this.service.songWithMostLike();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Nombre de playlists contenant une chanson",
            description = "Retourne le nombre de playlists qui contiennent une chanson donnée"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide")
    })
    @GetMapping(path="/HowManyPlaylistContainThisSong/{trackingIdSong}")
    public ResponseEntity<?> HowManyPlaylistContainThisSong(
            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.HowManyPlaylistContainThisSong(trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.titan.tune.Controller;

import com.titan.tune.Dto.Request.PlaylistRequest;
import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Service.ImplService.PlaylistServiceImpl;
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
@RequestMapping("/playlist")
@Tag(name = "Gestion des playlists", description = "Opérations CRUD et gestion des chansons dans les playlists")
public class PlaylistController {

    private final PlaylistServiceImpl service;

    public PlaylistController(PlaylistServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Créer une playlist", description = "Ajoute une nouvelle playlist pour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de requête invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/create/{trackingIdClient}")
    public ResponseEntity<?> create(
            @Parameter(description = "UUID du client", required = true)
            @PathVariable UUID trackingIdClient,

            @Parameter(description = "Données de la playlist à créer", required = true)
            @RequestBody PlaylistRequest request
    ) {
        var response = this.service.create(request, trackingIdClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour une playlist", description = "Met à jour une playlist existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PutMapping("/update/{trackingIdClient}/{trackingIdPlaylist}")
    public ResponseEntity<?> update(
            @Parameter(description = "UUID du client", required = true)
            @PathVariable UUID trackingIdClient,

            @Parameter(description = "UUID de la playlist", required = true)
            @PathVariable UUID trackingIdPlaylist,

            @Parameter(description = "Données mises à jour de la playlist", required = true)
            @RequestBody PlaylistRequest playlistRequest
    ) {
        var response = this.service.update(playlistRequest, trackingIdClient, trackingIdPlaylist);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtenir une playlist par UUID", description = "Récupère une playlist spécifique via son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/get/{trackingId}")
    public ResponseEntity<?> get(
            @Parameter(description = "UUID de la playlist", required = true)
            @PathVariable UUID trackingId
    ) {
        var response = this.service.get(trackingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Lister toutes les playlists", description = "Retourne toutes les playlists")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var response = this.service.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Lister les playlists d’un utilisateur", description = "Retourne toutes les playlists créées par un utilisateur donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlists récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/allForOne/{trackingIdClient}")
    public ResponseEntity<?> allForOne(
            @Parameter(description = "UUID du client", required = true)
            @PathVariable UUID trackingIdClient
    ) {
        var response = this.service.getAllForOne(trackingIdClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Changer la visibilité d’une playlist", description = "Permet de rendre une playlist publique ou privée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visibilité modifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PutMapping("/changeVisibilite/{trackingIdPlaylist}")
    public ResponseEntity<?> changeVisibilite(
            @Parameter(description = "UUID de la playlist", required = true)
            @PathVariable UUID trackingIdPlaylist
    ) {
        var response = this.service.changeVisibility(trackingIdPlaylist);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Ajouter une chanson à une playlist", description = "Ajoute une chanson spécifique à une playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson ajoutée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PostMapping("/addSong")
    public ResponseEntity<?> addSongIntoPlaylist(
            @Parameter(description = "Données de la chanson et de la playlist", required = true)
            @RequestBody SongPlaylistRequest request
    ) {
        var response = this.service.addSongToPlaylist(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Lister les chansons d’une playlist", description = "Retourne toutes les chansons contenues dans une playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/allSongForOne/{tracking_id_p}")
    public ResponseEntity<?> allSongForOnePlaylist(
            @Parameter(description = "UUID de la playlist", required = true)
            @PathVariable UUID tracking_id_p
    ) {
        var response = this.service.allSongForOnePlaylist(tracking_id_p);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer une chanson d’une playlist", description = "Retire une chanson d’une playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chanson supprimée avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PutMapping("/removeSongForPlaylist/{trackingIdPlaylist}/{trackingIdSong}")
    public ResponseEntity<?> removeSongIntoPlaylist(
            @Parameter(description = "UUID de la playlist", required = true)
            @PathVariable UUID trackingIdPlaylist,

            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        this.service.removeSongToPlaylist(trackingIdPlaylist, trackingIdSong);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Lister les playlists contenant une chanson", description = "Retourne toutes les playlists dans lesquelles une chanson apparaît")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlists récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @GetMapping("/getAllSongForPlaylist/{trackingIdSong}")
    public ResponseEntity<?> getAllPlaylistForOneSong(
            @Parameter(description = "UUID de la chanson", required = true)
            @PathVariable UUID trackingIdSong
    ) {
        var response = this.service.getAllPlaylistForOneSong(trackingIdSong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

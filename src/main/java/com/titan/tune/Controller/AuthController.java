package com.titan.tune.Controller;

import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Request.ClientsRequest;
import com.titan.tune.Dto.Request.LoginRequest;
import com.titan.tune.Dto.Response.ArtistResponse;
import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Dto.Response.UserAuthenticationResponse;
import com.titan.tune.Service.ImplService.UserServiceImpl;
import com.titan.tune.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin("*")
public class AuthController {

    private final UserServiceImpl userService;


    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/registerArtist")
    @Operation(summary = "Create a new artist user", description = "Creates a new artist and sends confirmation email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody ArtistsRequest request) {
        try {
            ArtistResponse response = userService.createArtist(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'artiste", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registerClient")
    @Operation(summary = "Inscription d'un nouveau client", description = "Crée un nouveau compte client")
    @ApiResponse(responseCode = "200", description = "client créé avec succès", content = @Content(schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "409", description = "Le client existe déjà")
    public ResponseEntity<?> inscriptionClient(@RequestBody ClientsRequest clientRequest) {
        try {
             ClientResponse response = userService.inscriptionClient(clientRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "INSCRIPTION_FAILED", "message", "Échec de l'inscription: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT token")
    public ResponseEntity<UserAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            UserAuthenticationResponse response = userService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Échec de l'authentification", e);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new UserAuthenticationResponse(
                            null, null, null, null,
                            null, null, null, null,
                            null, false
                    ));
        }
    }

    @PatchMapping("/etat/{trackingId}")
    @Operation(summary = "Update artist activation status", description = "Activates or deactivates an artist account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error or email sending failed")
    })
    public ResponseEntity<ArtistResponse> updateArtistEtat(
            @RequestParam boolean etat,
            @PathVariable UUID trackingId) {

        try {
            ArtistResponse response = userService.updateArtistEtat(trackingId, etat);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                log.error("Artiste non trouvé: {}", e.getMessage());
                return ResponseEntity.notFound().build();
            } else {
                log.error("Erreur lors de la mise à jour de l'état de l'artiste: {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'état de l'artiste", e);
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping(path = "/allClient")
    @Operation(summary = "get All client" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " list clients  got successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error or email sending failed")
    })
     public ResponseEntity<?> getAllClient(){
        var response = this.userService.allClient() ;
        return new ResponseEntity<>(response  , HttpStatusCode.valueOf(200));

     }

    @GetMapping(path = "/allArtist")
    @Operation(summary = "get All  artist" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " list  artist  got successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error or email sending failed")
    })
     public ResponseEntity<?> getAllArtist(){
        var response = this.userService.allArtiste() ;
        return  new ResponseEntity<>(response , HttpStatus.OK);
     }
}

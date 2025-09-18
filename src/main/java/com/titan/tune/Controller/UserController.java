package com.titan.tune.Controller;





import com.titan.tune.Dto.Request.UserRequest;
import com.titan.tune.Dto.Response.UserResponse;
import com.titan.tune.Service.ImplService.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

  /*  @PostMapping("/inscription")
    @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un nouveau compte utilisateur")
    @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "409", description = "L'utilisateur existe déjà")
    public ResponseEntity<?> inscription(@RequestBody UserRequest userRequest) {
        try {
            UserResponse response = userServiceImpl.inscription(userRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "INSCRIPTION_FAILED", "message", "Échec de l'inscription: " + e.getMessage()));
        }
    }*/

    @PostMapping("/activation")
    @Operation(summary = "Activer un compte utilisateur", description = "Active le compte utilisateur avec le code d'activation")
    public ResponseEntity<?> activation(@RequestBody Map<String, String> activationData) {
        try {
            userServiceImpl.activation(activationData);
            return ResponseEntity.ok(Map.of("message", "Compte activé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "ACTIVATION_FAILED", "message", "Échec de l'activation: " + e.getMessage()));
        }
    }

    @PostMapping("/demande-reinitialisation")
    @Operation(summary = "Demander une réinitialisation de mot de passe", description = "Envoie un email pour réinitialiser le mot de passe")
    public ResponseEntity<?> demanderReinitialisationMotDePasse(@RequestBody Map<String, String> emailMap) {
        try {
            userServiceImpl.modifierMotDePasse(emailMap);
            return ResponseEntity.ok(Map.of("message", "Email de réinitialisation envoyé"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "RESET_PASSWORD_FAILED", "message", "Échec de la demande de réinitialisation: " + e.getMessage()));
        }
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    @Operation(summary = "Réinitialiser le mot de passe", description = "Définit un nouveau mot de passe avec le token de réinitialisation")
    public ResponseEntity<?> reinitialiserMotDePasse(@RequestBody Map<String, String> resetData) {
        try {
            userServiceImpl.nouveauMotDePasse(resetData);
            return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "PASSWORD_RESET_FAILED", "message", "Échec de la réinitialisation: " + e.getMessage()));
        }
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Récupérer un utilisateur par son ID", description = "Récupère les détails d'un utilisateur par son ID de suivi")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable UUID trackingId) {
        try {
            UserResponse response = userServiceImpl.getUserByTrackingId(trackingId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", "Utilisateur non trouvé avec l'ID: " + trackingId));
        }
    }

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", description = "Récupère la liste de tous les utilisateurs enregistrés")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les informations d'un utilisateur existant")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID trackingId,
            @RequestBody UserResponse userResponse) {
        try {
            UserResponse response = userServiceImpl.updateUser(trackingId, userResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "UPDATE_FAILED", "message", "Échec de la mise à jour: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime définitivement un utilisateur du système")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID trackingId) {
        try {
            userServiceImpl.deleteUser(trackingId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "DELETE_FAILED", "message", "Échec de la suppression: " + e.getMessage()));
        }
    }
}

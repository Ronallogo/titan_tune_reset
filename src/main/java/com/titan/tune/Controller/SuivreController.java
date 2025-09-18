package com.titan.tune.Controller;


import com.titan.tune.Service.ImplService.SuivreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path="/suivre")
public class SuivreController {

    private final SuivreServiceImpl service ;

    public SuivreController(SuivreServiceImpl service) {
        this.service = service;
    }


    @Operation(
            summary = "follow un artiste",
            description =  "Ajouter l'utilisateur des followers de l'artiste"
    )
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    @PostMapping(path="/follow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<?> follow(
            @PathVariable UUID trackingIdFollower ,
            @PathVariable UUID trackingIdArtiste
    ){
        var response = this.service.follow(trackingIdFollower , trackingIdArtiste) ;
        return  new ResponseEntity<>( response ,  HttpStatusCode.valueOf(200))  ;
    }

    @Operation(
            summary = "Unfollow un artiste",
            description =  "Supprimer l'utilisateur des followers de l'artiste"
    )
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    @PutMapping(path = "/unfollow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<?> unfollow(
            @PathVariable UUID trackingIdFollower ,
            @PathVariable UUID trackingIdArtiste
    ){
        this.service.unfollow(trackingIdFollower , trackingIdArtiste);
        return  new ResponseEntity<>(HttpStatusCode.valueOf(200));

    }


    @GetMapping(path = "/nbrFollowers/{trackingIdArtiste}")
    public ResponseEntity<?> nbrFollowersForOneArtiste(@PathVariable UUID trackingIdArtiste){
        var value = this.service.nbrFollowers(trackingIdArtiste) ;
        Map<String , Object> response = new HashMap<>() ;
        response.put("nombre_followers" , value) ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;

    }

    @GetMapping(path="/nbrFollowings/{trackingIdClient}")
    public ResponseEntity<?> nbrFollowingForOneUser(
            @PathVariable UUID trackingIdClient
    ){
        var value = this.service.nbrFollowing(trackingIdClient) ;
        Map<String , Object> response = new HashMap<>() ;
        response.put("nombre_following" , value) ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;

    }




    @GetMapping(path = "/followers/{trackingIdArtiste}")
    public ResponseEntity<?> allFollowerArtiste(@PathVariable UUID trackingIdArtiste){

        var response = this.service.followers(trackingIdArtiste);

        return  new ResponseEntity<>(response , HttpStatus.OK);

    }

    @GetMapping(path = "/followings/{trackingIdClient}")
    public ResponseEntity<?> allFollowing(@PathVariable  UUID trackingIdClient){
        var response = this.service.following(trackingIdClient) ;
        return  new ResponseEntity<>(response , HttpStatus.OK);

    }


}

package com.titan.tune.Controller;

import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Service.ImplService.StatistiqueServiceImpl;
import com.titan.tune.Service.StatistiqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping(path="/stats")
public class StatistiqueController {
    private final StatistiqueServiceImpl service ;

    public StatistiqueController(StatistiqueServiceImpl service) {
        this.service = service;
    }

    @GetMapping(path="/nbrUserEnabled")
    public ResponseEntity<?> nbruserEnabled(){

        var response = this.service.nbrUserEnabled() ;
        return new ResponseEntity<>(response ,  HttpStatus.OK);

    }

    @GetMapping(path = "/nbrLikeForOneSong/{trackingIdSong}")
    public ResponseEntity<?> nbrLikeForOneSong(@PathVariable UUID trackingIdSong){
        var response = this.service.nbrLikeForOneSong(trackingIdSong);

        return  new ResponseEntity<>(response , HttpStatus.OK);

    }

    @GetMapping(path = "/nbrTotalEcoute/{trackingIdSong}")
    public ResponseEntity<?> nbrTotalEcoute(@PathVariable UUID trackingIdSong){
        var response = this.service.nbrTotalEcouteForOneSong(trackingIdSong);
        return new ResponseEntity<>(response , HttpStatus.OK) ;
    }

    @GetMapping(path="/artistMostListen")
    public ResponseEntity<?> artistMostListen(){
        var response = this.service.artistMostListen() ;

        return  new ResponseEntity<>(response   , HttpStatus.OK);
    }



    @GetMapping(path="/songMostListen")
    public ResponseEntity<?> songMostListen(){
        var response = this.service.songMostListen() ;
        return  new ResponseEntity<>(response , HttpStatus.OK);

    }

    @GetMapping(path="/songWithMostLike")
    public ResponseEntity<?> songWithMostLike(){
        var response = this.service.songWithMostLike() ;

        return  new ResponseEntity<>(response , HttpStatus.OK);
    }


    @GetMapping(path="/HowManyPlaylistContainThisSong/{trackingIdSong}")
    @Operation(summary = "How many playlist contains this specific song" )
    @ApiResponse(responseCode = "200", description = "data reached successfully" )
    @ApiResponse(responseCode = "400", description = "invalid input")

    public ResponseEntity<?>HowManyPlaylistContainThisSong(@PathVariable UUID trackingIdSong){
        var response = this.service.HowManyPlaylistContainThisSong(trackingIdSong);

        return  new ResponseEntity<>(response , HttpStatus.OK) ;
    }
}

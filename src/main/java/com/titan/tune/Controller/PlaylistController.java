package com.titan.tune.Controller;

import com.titan.tune.Dto.Request.PlaylistRequest;
import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Service.ImplService.PlaylistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/playlist")
public class PlaylistController {

    private final PlaylistServiceImpl service ;

    public PlaylistController(PlaylistServiceImpl service) {
        this.service = service;
    }


    @PostMapping(path="/create/{trackingIdClient}")
    @Operation(summary = "add new playlist" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  playlist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody PlaylistRequest request ,
                                    @PathVariable UUID trackingIdClient ){

        var response = this.service.create(request , trackingIdClient);
        return  new ResponseEntity<>(response , HttpStatus.OK);

    }

    @Operation(summary = "update playlist" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  playlist  updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(path="/update/{trackingIdClient}/{trackingIdPlaylist}")
    public ResponseEntity<?> update(
            @RequestBody PlaylistRequest playlistRequest ,
            @PathVariable UUID trackingIdClient ,
            @PathVariable  UUID trackingIdPlaylist
    ) {
        var response = this.service.update(
                playlistRequest ,
                trackingIdClient ,
                trackingIdPlaylist
        );

        return  new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }


    @GetMapping(path="/get/{trackingId}")
    @Operation(summary = " get    playlist by tracking id" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  playlist  got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> get(@PathVariable UUID trackingId){
        var response = this.service.get(trackingId) ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;
    }

    @GetMapping(path="/all")
    @Operation(summary = " get all    playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " list  playlist  got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAll(){
        var response = this.service.getAll() ;
        return  new ResponseEntity<>(response ,  HttpStatus.OK) ;
    }


    @Operation(summary = " get all    playlist for one client whit his tracking id" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  list playlist  got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/allForOne/{trackingIdClient}")
    public ResponseEntity<?> allForOne(@PathVariable UUID trackingIdClient){
        var response = this.service.getAllForOne(trackingIdClient);
        return new ResponseEntity<>(response ,  HttpStatus.OK) ;
    }


    @Operation(summary = " setup playlist's visibility    " )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  visibility  changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(path="/changeVisibilite/{trackingIdPlaylist}")
    public  ResponseEntity<?> changeVisibilite(@PathVariable UUID trackingIdPlaylist){

        var response = this.service.changeVisibility(trackingIdPlaylist);

        return new ResponseEntity<>(response , HttpStatus.OK);

    }



    @PostMapping(path="/addSong")
    public ResponseEntity<?> addSongIntoPlaylist(@RequestBody SongPlaylistRequest request){

        var response = this.service.addSongToPlaylist(request) ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;

    }

    @GetMapping(path="/allSongForOne/tracking_id_playlist/{tracking_id_p}")
    public ResponseEntity<?> allSongForOnePlaylist(@PathVariable UUID tracking_id_playlist){
        var response = this.service.allSongForOnePlaylist(tracking_id_playlist);
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }

}

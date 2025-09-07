package com.titan.tune.Controller;


import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/song")
@Slf4j
@CrossOrigin("*")
public class SongController {

    private final SongService service ;

    public SongController(SongService service) {
        this.service = service;
    }


    @PostMapping(path="/create")
    @Operation(summary = "register song", description = "registration of new song")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Song created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody SongRequest request){
            var response = this.service.create(request) ;
            return new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }

    @GetMapping(path="/get/{trackingIdSong}")
    @Operation(summary = "get song", description = "get song by its tracking id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "song  got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> get(@PathVariable UUID trackingIdSong){
        var response = this.service.getSong(trackingIdSong) ;
        return  new ResponseEntity<>(response  , HttpStatus.OK) ;
    }


    @PostMapping(path="/update/{trackingIdSong}")
    @Operation(summary = "update song", description = " update song by its tracking id and the data modification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "song  updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> update(
            @RequestBody SongRequest request ,
            @PathVariable UUID trackingIdSong){

        var response = this.service.update(trackingIdSong , request);
        return  new ResponseEntity<>(response , HttpStatus.OK) ;

    }




    @GetMapping(path="/songsForOnrArtiste/{trackingIdArtiste}")
    @Operation(summary = " all song for an artiste", description = "  all song for an artiste by  his tracking id  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "songs got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllArtisteSong(@PathVariable UUID trackingIdArtiste){

        var response = this.service.getSongsByArtiste(trackingIdArtiste) ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;
    }


    @DeleteMapping(path="/delete/{trackingIdSong}")
    @Operation(summary = " delete song", description = "  delete song by tracking id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "song  delete successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> delete(@PathVariable UUID trackingId){
        this.service.delete(trackingId);
        return  new ResponseEntity<>(  HttpStatus.OK);
    }



}

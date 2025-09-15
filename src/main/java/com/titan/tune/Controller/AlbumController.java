package com.titan.tune.Controller;


import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/album")
@Slf4j
@CrossOrigin("*")
public class AlbumController {

    private final AlbumService service ;

    public AlbumController(AlbumService service) {
        this.service = service;
    }



    @PostMapping(path="/create")
    @Operation(summary = "register   album", description = "registration of new  album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " album created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(
            @RequestBody AlbumRequest request
          ){
        var response = this.service.create(request ) ;
        return  new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }

    @Operation(summary = " get   album", description = " fetch a  album with its tracking id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " album created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/get/{trackingIdAlbum}")
    public ResponseEntity<?> get(@PathVariable UUID trackingIdAlbum){
        var response = this.service.getAlbum(trackingIdAlbum);
        return new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }


    @GetMapping(path="/getAll")
    public ResponseEntity<?> getAll(){

        var response = this.service.listeAlbums() ;
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(path="/update/{trackingIdAlbum}")
    public ResponseEntity<?> update(
            @PathVariable UUID trackingIdAlbum ,
            @RequestBody AlbumRequest request){
        var response = this.service.update(trackingIdAlbum , request);
        return  new ResponseEntity<>( response ,  HttpStatus.OK);

    }

    @DeleteMapping(path = "/delete/{trackingIdAlbum}")
    public ResponseEntity<?> delete(@PathVariable UUID trackingIdAlbum){
         this.service.delete(trackingIdAlbum);

        return new ResponseEntity<>(  HttpStatus.OK) ;
    }
}

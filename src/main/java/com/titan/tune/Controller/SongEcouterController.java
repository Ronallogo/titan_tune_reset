package com.titan.tune.Controller;


import com.titan.tune.Service.ImplService.SongEcouterServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/songEcouter")
public class SongEcouterController {

    private final SongEcouterServiceImpl service ;

    public SongEcouterController(SongEcouterServiceImpl service) {
        this.service = service;
    }


    @PostMapping( path = "/register/{trackingIdUser}/{trackingIdSong}")
    public ResponseEntity<?> register(
            @PathVariable UUID trackingIdUser ,
            @PathVariable UUID trackingIdSong
    ){
        var response = this.service.create(trackingIdUser , trackingIdSong) ;
        return   new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }


    @GetMapping(path="/all")
    public ResponseEntity<?> getAll(){
        var response = this.service.getAll() ;
        return  new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }

    @GetMapping(path="/allForOne/{trackingIdUser}")
    public ResponseEntity<?> allForOne(@PathVariable UUID trackingIdUser){
        var response = this.service.getAllForOne(trackingIdUser);
        return  new ResponseEntity<>(response , HttpStatusCode.valueOf(200));
    }

    @GetMapping(path = "/search/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title){
        var response = this.service.searchByTitle(title) ;
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }


}

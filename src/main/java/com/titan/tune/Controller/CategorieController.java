package com.titan.tune.Controller;


import com.titan.tune.Dto.Request.CategorieRequest;
import com.titan.tune.Dto.Response.CategorieResponse;
import com.titan.tune.Service.CategorieService;
import com.titan.tune.Service.ImplService.CategorieServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorie")
@Slf4j
@CrossOrigin("*")
public class CategorieController {

    private final CategorieServiceImpl service ;

    public CategorieController(CategorieServiceImpl service) {
        this.service = service;
    }



    @PostMapping(path="/create")
    @Operation(summary = "register  categorie", description = "registration of new  categorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " categorie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody CategorieRequest request){
        var response = this.service.addCategorie(request) ;
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }


    @PostMapping(path="/update/{trackingIdCategorie}")
    @Operation(summary = "register  categorie", description = "registration of new  categorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " categorie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> update(
            @RequestBody CategorieRequest request ,
            @PathVariable UUID trackingIdCategorie
            ){
        var response = this.service.updateCategorie(trackingIdCategorie , request);
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }


    @GetMapping(path="/get/{trackingId}")
    @Operation(summary = " get  categorie", description = "registration of new categorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categorie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> get(@PathVariable UUID trackingId){
        var  response = this.service.findByTrackingId(trackingId) ;

        return  new ResponseEntity<>(response , HttpStatus.OK);
    }

    @GetMapping(path="/getAll")
    @Operation(summary = "  all  categories", description = " get  all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categories  got successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAll(){
        var response = this.service.listCategories() ;
        return  new ResponseEntity<>(response , HttpStatus.OK) ;
    }

    @DeleteMapping(path="/delete/{trackingIdCategorie}")
    @Operation(summary = "   delete  categorie", description = "  delete  categorie by its tracking id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categorie   deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> delete(UUID trackingIdCategorie){
       this.service.deleteByTrackingId(trackingIdCategorie);
        return new ResponseEntity<>(  HttpStatus.OK) ;
    }

    public ResponseEntity<?>
}

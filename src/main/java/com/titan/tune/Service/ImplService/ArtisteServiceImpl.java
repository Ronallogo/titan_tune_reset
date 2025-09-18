package com.titan.tune.Service.ImplService;


import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Response.ArtistResponse;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Mapper.ArtistMapper;
import com.titan.tune.Repositories.ArtistRepository;
import com.titan.tune.Repositories.UserRepository;
import com.titan.tune.Service.ArtisteService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ArtisteServiceImpl implements ArtisteService {

    private final ArtistRepository  repository ;

    private final ArtistMapper mapper ;

    public ArtisteServiceImpl( ArtistRepository  repository, ArtistMapper mapper) {
        this.repository =  repository;
        this.mapper = mapper;
    }

    @Override
    public ArtistResponse create(ArtistsRequest request) {

        Assert.notNull(request , "request must b  not null");

        Artiste artiste = this.mapper.toEntity(request);
        artiste.setTrackingId(UUID.randomUUID());

        var result = this.repository.save(artiste);


        return this.mapper.toResponse(result);
    }

    @Override
    public ArtistResponse update(ArtistsRequest request, UUID trackingId) {
        Assert.notNull(trackingId , "tracking id must be not null");
        Assert.notNull(request , "request must be not null");


        Artiste artiste = this.repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "artiste not found")) ;


        artiste = this.mapper.updateArtist(request , artiste) ;

        var result = this.repository.save(artiste);

        return  this.mapper.toResponse(result);
    }

    @Override
    public List<ArtistResponse> getAll() {
        return this.repository.findAllArtiste()
                .stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID trackingId) {
        Assert.notNull(trackingId  , "trackingId must be not null");

        var result = this.repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.OK , "artiste not found")) ;

        this.repository.delete(result);

    }

    @Override
    public ArtistResponse get(UUID trackingId) {
        Assert.notNull(trackingId  , "trackingId must be not null");
        var result = this.repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.OK , "artiste not found")) ;


        return this.mapper.toResponse(result);
    }
}

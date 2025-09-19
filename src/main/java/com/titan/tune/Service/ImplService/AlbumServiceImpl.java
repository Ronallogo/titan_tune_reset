package com.titan.tune.Service.ImplService;

import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Dto.Response.AlbumResponse;
import com.titan.tune.Entity.Album;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Mapper.AlbumMapper;
import com.titan.tune.Repositories.AlbumRepository;
import com.titan.tune.Repositories.ArtistRepository;
import com.titan.tune.Service.AlbumService;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@Service
public class AlbumServiceImpl implements AlbumService {

    private final ArtistRepository artisteRepository ;

    private final AlbumRepository repository ;

    private final AlbumMapper mapper ;

    public AlbumServiceImpl(ArtistRepository artisteRepository, AlbumRepository repository, AlbumMapper mapper) {
        this.artisteRepository = artisteRepository;
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public AlbumResponse create( AlbumRequest request  ) {
        Assert.notNull( request ,  "request must be not null");

        Artiste artiste = this.artisteRepository.findByTrackingId(request.artisteTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND  , "artiste not found"));

        Album album = this.mapper.toEntity(request , artiste) ;
        album.setTrackingId(UUID.randomUUID());

        var result = this.repository.save(album);

        return this.mapper.toResponse(result) ;
    }

    @Override
    public AlbumResponse getAlbum(UUID trackingId) {

        Assert.notNull(trackingId , " album tracking id must be not null");

        var result = this.repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "album not found")) ;

        return  this.mapper.toResponse(result) ;
    }

    @Override
    public List<AlbumResponse> getAllForOneArtiste(UUID trackingIdAlbum) {
        return List.of();
    }

    @Override
    public  void delete(UUID trackingId) {

         var album = this.repository.findByTrackingId(trackingId)
                 .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "album  not found"));

         this.repository.delete(album);

    }

    @Override
    public List<AlbumResponse> listeAlbums() {
        return  this.repository.getAll().stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    public AlbumResponse update(UUID trackingId, AlbumRequest request) {

        Assert.notNull(trackingId , "tracking id must be not null");
        Assert.notNull(request , "request must be not null");


        Album album = this.repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "album not found")) ;


        Artiste artiste = this.artisteRepository.findByTrackingId(request.artisteTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , " artiste not found")) ;

        album = this.mapper.toEntityByUpdate(album , request , artiste);
        var result = this.repository.save(album);

        return this.mapper.toResponse(result);
    }


}

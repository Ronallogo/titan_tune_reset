package com.titan.tune.Service.ImplService;

import com.titan.tune.Dto.Request.FavorisRequest;
import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Dto.Response.FavorisResponse;
import com.titan.tune.Dto.Response.SongPlaylistResponse;
import com.titan.tune.Entity.*;
import com.titan.tune.Mapper.FavorisMapper;
import com.titan.tune.Mapper.PlaylistMapper;
import com.titan.tune.Mapper.SongMapper;
import com.titan.tune.Mapper.SongPlaylistMapper;
import com.titan.tune.Repositories.*;
import com.titan.tune.Service.FavorisService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import java.util.UUID;

@Service
public class FavorisServiceImpl implements FavorisService {

    private final FavorisRepository favorisRepository;
    private final FavorisMapper favorisMapper;
    private final SongRepository songRepository ;

    private final ClientRepository clientRepository ;

    public FavorisServiceImpl(FavorisRepository favorisRepository, FavorisMapper favorisMapper, SongRepository songRepository, ClientRepository clientRepository) {
        this.favorisRepository = favorisRepository;
        this.favorisMapper = favorisMapper;
        this.songRepository = songRepository;
        this.clientRepository = clientRepository;
    }
    @Override
    public FavorisResponse addFavoris(FavorisRequest request) {
        Assert.notNull(request  , " request must be not null");

        Clients client = this.clientRepository.findByTrackingId(request.ClientTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "client not found"));

        Song song = this.songRepository.findByTrackingId(request.SongTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,  "song not found"));


        Favoris favoris = favorisMapper.toEntity(client , song);
        favoris.setTrackingId(UUID.randomUUID());

        Favoris saveFavoris = favorisRepository.save(favoris);
        return favorisMapper.toResponse(saveFavoris);



    }

    @Override
    public List<FavorisResponse> getAllFavoris() {
        return favorisRepository.findAllByFavoris()
                .stream()
                .map(favorisMapper::toResponse)
                .toList();
    }

    @Override
    public FavorisResponse getFavorisByTrackingId(UUID trackingId) {
        Assert.notNull(trackingId , "tracking id must be not null");

        Favoris favoris = favorisRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("No favoris found with this trackingId"));

        return favorisMapper.toResponse(favoris);
    }

    @Override
    public void deleteFavoris(UUID trackingId) {
        Assert.notNull(trackingId , "tracking id must be not null");

        Favoris favoris = favorisRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("No favoris found with this trackingId"));
        favorisRepository.delete(favoris);

    }
}

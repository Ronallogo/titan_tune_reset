package com.titan.tune.Service.ImplService;

import com.titan.tune.Dto.Response.SongEcouterForOneUser;
import com.titan.tune.Dto.Response.SongEcouterResponse;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Song;
import com.titan.tune.Entity.SongEcouter;
import com.titan.tune.Entity.User;
import com.titan.tune.Mapper.SongEcouterMapper;
import com.titan.tune.Repositories.SongEcouterRepository;
import com.titan.tune.Repositories.SongRepository;
import com.titan.tune.Repositories.UserRepository;
import com.titan.tune.Service.SongEcouterService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SongEcouterServiceImpl implements SongEcouterService {

    private final SongEcouterRepository repository ;
    private final UserRepository userRepository ;
    private final SongRepository songRepository ;
    private  final SongEcouterMapper mapper ;

    public SongEcouterServiceImpl(SongEcouterRepository repository, UserRepository userRepository, SongRepository songRepository, SongEcouterMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.mapper = mapper;
    }




    @Override
    public SongEcouterResponse create(UUID trackingIdUser, UUID trackingIdSong) {

        Assert.notNull(trackingIdSong  , "tracking id song must not be null");

        Assert.notNull(trackingIdUser , "tracking Id user must not be null");

        User user  = this.userRepository.findByTrackingId(trackingIdUser)
                .orElseThrow(()-> new RuntimeException("user introuvable")) ;

        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()-> new RuntimeException("song introuvable"));

        SongEcouter s = this.mapper.toEntity(song , user);

        s.setTrackingId(UUID.randomUUID());

        var result =  this.repository.save(s) ;

        return this.mapper.toResponse(s);
    }

    @Override
    public void delete(UUID trackingIdSongEcouter) {
        SongEcouter s = this.repository.findBytrackingId(trackingIdSongEcouter)
                .orElseThrow(()-> new RuntimeException("ce song n'est plus dans la liste d'écoute de l'utilisateur"));

        this.repository.delete(s);
    }



    @Override
    public List<SongEcouterForOneUser> searchForOneBytrackingId(UUID trackingIdUser) {
        User user = this.userRepository.findByTrackingId(trackingIdUser)
                .orElseThrow(()-> new RuntimeException("utilisateur inrouvable")) ;

        List<SongEcouter> listSongEcouter = this.repository.findAllByTrackingIdUser(user.getTrackingId()) ;
/// //verifions le son  aussi.
        return  listSongEcouter
                .stream()
                .map(this.mapper::toResponseSearch)
                .toList() ;
    }


    public List<SongEcouterResponse> searchByTitle(String titreSong) {

        List<SongEcouter> songEcouterList = this.repository.findByTitle(titreSong);

        return  songEcouterList.stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    public SongEcouterResponse get(UUID trackingIdSongEcouter) {

        SongEcouter song  = this.repository.findByTrackingId(trackingIdSongEcouter)
                .orElseThrow(()-> new RuntimeException("ce son n' est pas écouté ou n'est pas valide"));
        return  this.mapper.toResponse(song);

    }

    @Override
    public List<SongEcouterResponse> getAll() {
        List<SongEcouter> list = this.repository.findAll() ;
        return   list.stream()
                .map(this.mapper::toResponse)
                .toList() ;
    }

    @Override
    public List<SongEcouterForOneUser> getAllForOne(UUID trackingIdUser) {


        var user = this.userRepository.findByTrackingId(trackingIdUser)
                .orElseThrow(()-> new RuntimeException("trackingId invalide")) ;

        List<SongEcouter> list = this.repository.findAllByTrackingIdUser(user.getTrackingId()) ;
        return  list.stream()
                .map(this.mapper::toResponseSearch)
                .toList() ;
    }
}

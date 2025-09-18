package com.titan.tune.Service.ImplService;


import com.titan.tune.Dto.Request.PlaylistRequest;
import com.titan.tune.Dto.Request.SongPlaylistRequest;
import com.titan.tune.Dto.Response.PlaylistResponse;
import com.titan.tune.Dto.Response.SongPlaylistResponse;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Clients;
import com.titan.tune.Entity.Playlist;
import com.titan.tune.Entity.Song;
import com.titan.tune.Entity.SongPlaylist;
import com.titan.tune.Mapper.PlaylistMapper;
import com.titan.tune.Mapper.SongMapper;
import com.titan.tune.Mapper.SongPlaylistMapper;
import com.titan.tune.Repositories.*;
import com.titan.tune.Service.PlaylistService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository repository;
    private final SongRepository songRepository;
   private final SongPlaylistMapper songPlaylistMapper;

    private final SongMapper songMapper;

    private final SongPlaylistRepository songPlaylistRepository;

    private final ClientRepository clientRepository;
    private final PlaylistMapper mapper;


    public PlaylistServiceImpl(PlaylistRepository repository, SongRepository songRepository, SongPlaylistMapper songPlaylistMapper, SongMapper songMapper, SongPlaylistRepository songPlaylistRepository, ClientRepository clientRepository, PlaylistMapper mapper) {
        this.repository = repository;
        this.songRepository = songRepository;
        this.songPlaylistMapper = songPlaylistMapper;
        this.songMapper = songMapper;
        this.songPlaylistRepository = songPlaylistRepository;
        this.clientRepository = clientRepository;

        this.mapper = mapper;
    }


    @Override
    public SongPlaylistResponse addSongToPlaylist(SongPlaylistRequest request) {
        Playlist playlist = this.repository.findByTrackingId(request.trackingIdPlaylist())
                .orElseThrow(() -> new RuntimeException("playlist introuvable ou tracking Id invalide"));

        Song song = this.songRepository.findByTrackingId(request.trackingIdSong())
                .orElseThrow(() -> new RuntimeException("song introuvable ou tracking id invalide"));

        SongPlaylist songPlaylist = this.songPlaylistMapper.toEntity(  song, playlist);


        if (!this.ifSongAlreadyInPlaylist(song, playlist)) {
            songPlaylist.setTrackingId(UUID.randomUUID());
        }
        songPlaylist.setInside(true);


        var response = this.songPlaylistRepository.save(songPlaylist);

        response.setTrackingId(UUID.randomUUID());


        return this.songPlaylistMapper.toResponse(response);
    }

    @Override
    public void removeSongToPlaylist(UUID trackingIdPlaylist, UUID trackingIdSong) {

        if (trackingIdPlaylist == null) throw new IllegalArgumentException("trackingIdPlaylist est null");

        if (trackingIdSong == null) throw new IllegalArgumentException("trackingIdSong est null");

        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(() -> new RuntimeException("song introuvable ou tracking id invalide"));

        Playlist playlist = this.repository.findByTrackingId(trackingIdPlaylist)
                .orElseThrow(() -> new RuntimeException("playlist est introuvable ou tracking id invalide"));


        SongPlaylist songPlaylist = this.songPlaylistRepository.findByCombineId(song.getSongId(), playlist.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("ce song n'est plus dans ce playlist"));

        songPlaylist.setInside(false);


        this.songPlaylistRepository.save(songPlaylist);

    }


    @Override
    public List<SongResponse> getAllSongForPlaylist(UUID trackingIdPlaylist) {

        Playlist  p = this.repository.findByTrackingId(trackingIdPlaylist)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "playlist not found"));

        List<Song> listSong = this.songRepository.findAllByPlaylistId(p.getPlaylistId());

        return listSong.stream().map(this.songMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlaylistResponse> getAllPlaylistForOneSong(UUID trackingIdSong) {
        Assert.notNull(trackingIdSong , "tracking id must be not null");

        Song  song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "song not found"));

      //  List<Playlist> list = this.songRepository.

        return List.of();
    }

    @Override
    public PlaylistResponse create(PlaylistRequest  request, UUID trackingIdClient) {


        Assert.notNull(trackingIdClient , "client must be not null");

         Clients client = this.clientRepository.findByTrackingId(trackingIdClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "client not found"));

        Playlist p = this.mapper.toEntity( request);
        p.setTrackingId(UUID.randomUUID());
        p.setClient(client);
        p = this.repository.save(p);

        return this.mapper.toResponse(p);
    }

    @Override
    public List<PlaylistResponse> getAllForOne(UUID trackingIdClient) {
         Clients client = this.clientRepository.findByTrackingId(trackingIdClient)
                .orElseThrow(() -> new RuntimeException("client introuvable ou tracking ID invalide"));
        List<Playlist> list = this.repository.findAllForOne(client.getId());
        return this.mapper.toResponseList(list);
    }

    @Override
    public boolean ifSongAlreadyInPlaylist(Song song, Playlist playlist) {

        var result = this.songPlaylistRepository.findByCombineId(song.getSongId(), playlist.getPlaylistId())
                .orElse(null);

        return result != null;

    }

    @Override
    public PlaylistResponse update(PlaylistRequest playlist,
                                   UUID trackingIdClient,
                                   UUID trackingIdPlaylist) {

        if (trackingIdClient == null) throw new IllegalArgumentException("tracking Id client null");

        if (trackingIdPlaylist == null) throw new IllegalArgumentException("tracking Id  playlist null");

        Playlist p = this.repository.findByTrackingId(trackingIdPlaylist)
                .orElseThrow(() -> new RuntimeException("playlist introuvable"));

         Clients client = this.clientRepository.findByTrackingId(trackingIdClient)
                .orElseThrow(() -> new RuntimeException("client introuvable"));

        p.setTitre(playlist.titre());

        Playlist playlistUpdated = this.repository.save(p);

        playlistUpdated.setClient(client);

        return this.mapper.toResponse(playlistUpdated);
    }

    @Override
    public PlaylistResponse get(UUID trackingId) {

        if (trackingId == null) throw new IllegalArgumentException("tracking Id  playlist null");

        Playlist p = this.repository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("playlist introuvable"));

        return this.mapper.toResponse(p);
    }

    @Override
    public List<PlaylistResponse> getAll() {
        List<Playlist> list = this.repository.getAll();
        return this.mapper.toResponseList(list);
    }

    @Override
    public void delete(UUID trackingId) {

        Playlist p = this.repository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("playlist introuvable"));
        this.repository.delete(p);

    }

    @Override
    public List<PlaylistResponse> findByTitle(String title) {
        List<Playlist> listPlalist = this.repository.findByTitre(title);

        if (listPlalist.isEmpty()) return Collections.emptyList();

        return listPlalist.stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    public PlaylistResponse changeVisibility(UUID trackingIdPlaylist) {
        Playlist playlist = this.repository.findByTrackingId(trackingIdPlaylist)
                .orElseThrow(() -> new RuntimeException("playlist introuvable ou tracking Id invalide"));

        playlist.setVisibilite(!playlist.isVisibilite());
        playlist = this.repository.save(playlist);

        return this.mapper.toResponse(playlist);
    }

    @Override
    public List<SongResponse> allSongInPlaylist() {

        List<Song> listSong = this.songPlaylistRepository.allSongInPlaylist() ;

        return  listSong.stream().map(this.songMapper::toResponse)
                .toList();
    }

    @Override
    public List<SongResponse> allSongForOnePlaylist(UUID tracking_id_playlist) {
        if (tracking_id_playlist == null) throw new RuntimeException("tracking id null");

        Playlist p = this.repository.findByTrackingId(tracking_id_playlist)
                .orElseThrow(() -> new RuntimeException("playlist introuvable ou tracking id invalide"));

        List<Song> result = this.repository.findAllSongForOnePlaylist(p.getPlaylistId());
        return result.stream()
                .map(this.songMapper::toResponse)
                .toList();
    }



}

package com.titan.tune.Service.ImplService;

import com.titan.tune.Entity.Song;
import com.titan.tune.Repositories.*;
import com.titan.tune.Service.StatistiqueService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class StatistiqueServiceImpl implements StatistiqueService {
    private final UserRepository userRepository ;
    private  final SongRepository songRepository ;

    private final SongPlaylistRepository songPlaylistRepository ;
    private final SongEcouterRepository songEcouterRepository ;
    private final PlaylistRepository playlistRepository ;

    public StatistiqueServiceImpl(UserRepository userRepository, SongRepository songRepository, SongPlaylistRepository songPlaylistRepository, SongEcouterRepository songEcouterRepository, PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.songPlaylistRepository = songPlaylistRepository;
        this.songEcouterRepository = songEcouterRepository;
        this.playlistRepository = playlistRepository;
    }




    @Override
    public Map<String, Object> nbrUserEnabled() {

        Integer value = this.userRepository.totalUserActif() ;
        Map<String , Object> response =  new HashMap<>() ;
        response.put("nombre_user_actif" , value) ;
        return  response;
    }

    @Override
    public Map<String, Object> tauxRetention(UUID trackingIdSong) {

        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()-> new IllegalArgumentException("song introuvable"));



        return Map.of();
    }

    @Override
    public Map<String, Object> nbrTotalEcouteForOneSong(UUID trackingIdSong) {
        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()-> new IllegalArgumentException("song introuvable ou tracking Id non valide"));

        return  this.songEcouterRepository.nbrTotalEcouterForOneSong(song.getSongId());
    }

    @Override
    public Map<String, Object> HowManyPlaylistContainThisSong(UUID trackingIdSong) {

        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()-> new IllegalArgumentException("song introuvable ou tracking Id non valide"));

        return this.songPlaylistRepository.HowManyPlaylistContainThisSong(song.getSongId());
    }

    @Override
    public List<Map<String, Object>> howMayTimeEachSongWasListen() {
        return this.songEcouterRepository.howMayTimeEachSongWasListen();
    }

    @Override
    public Map<String, Object> artistMostListen() {
        return  this.songEcouterRepository.artisteMostListen();
    }

    @Override
    public Map<String, Object> songMostListen() {
        return this.songEcouterRepository.songMostListen();
    }

    @Override
    public Map<String, Object> nbrLikeForOneSong(UUID trackingIdSong) {
        Song song = this.songRepository.findByTrackingId(trackingIdSong)
                .orElseThrow(()-> new IllegalArgumentException("  song introuvable ou  tracking Id invalide "));

        return this.songEcouterRepository.nbrLikeForOneSong(song.getSongId());
    }

    @Override
    public Map<String, Object> songWithMostLike() {
        return this.songEcouterRepository.songWithMostLike();
    }

    @Override
    public List<Map<String, Object>> DemographicDataList() {
        return List.of();
    }
}

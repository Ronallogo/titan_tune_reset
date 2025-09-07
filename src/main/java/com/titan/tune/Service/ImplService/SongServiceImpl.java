package com.titan.tune.Service.ImplService;


import com.titan.tune.Dto.Request.SongRequest;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Album;
import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;
import com.titan.tune.Mapper.SongMapper;
import com.titan.tune.Repositories.AlbumRepository;
import com.titan.tune.Repositories.ArtistRepository;
import com.titan.tune.Repositories.CategorieRepository;
import com.titan.tune.Repositories.SongRepository;
import com.titan.tune.Service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    private final CategorieRepository categorieRepository ;
    private final AlbumRepository albumRepository ;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper, CategorieRepository repository, CategorieRepository categorieRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.categorieRepository = categorieRepository;


        this.albumRepository = albumRepository;
    }


    @Override
    public SongResponse getSong(UUID trackingId)  {
        Song song =  songRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
        return songMapper.toResponse(song);
    }



    @Override
    public List<SongResponse> findByNomCategorieContaining(String nomCategorie) {
        return this.songRepository.findByNomCategorieContaining(nomCategorie)
                .stream().map(this.songMapper::toResponse)
                .toList();
    }


    @Override
    public List<SongResponse> getSongsByArtiste(UUID artisteTrackingId) {
        List<Song> songs = songRepository.findByArtisteTrackingId(artisteTrackingId);
        return songs.stream()
                .map(songMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SongResponse create(SongRequest request) {

        Categorie categorie = this.categorieRepository.findByTrackingId(request.categorieTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "categorie not found"));

        Album album = this.albumRepository.findByTrackingId(request.albumTrackingId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "album not found"));

        Song song = this.songMapper.toEntity(request , album , categorie );

        var result   = this.songRepository.save(song);

        return this.songMapper.toResponse(result);

    }

    @Override
    public SongResponse update(UUID trackingId, SongRequest request) {
        Song song =   songRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        Album album = this.albumRepository.findByTrackingId(request.albumTrackingId())
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "album not found"));

        Categorie categorie = this.categorieRepository.findByTrackingId(request.categorieTrackingId())
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND  , "categorie not found"));
        song =   songMapper.toEntityUpdate( request,song ,album , categorie   );

         var result = songRepository.save(song);
        return songMapper.toResponse( result);


    }

    @Override
    public  void delete(UUID trackingId) {
        songRepository.deleteByTrackingId(trackingId);
    }

    @Override
    public List<SongResponse> getAll() {

        return  this.songRepository.getAll()
                .stream().map(this.songMapper::toResponse)
                .toList();
    }


}

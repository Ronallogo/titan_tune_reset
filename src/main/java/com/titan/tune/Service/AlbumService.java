package com.titan.tune.Service;

import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Dto.Response.AlbumResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AlbumService {
    AlbumResponse create(AlbumRequest request  );

    AlbumResponse getAlbum(UUID trackingId);



     void delete(UUID trakingId);


     List<AlbumResponse> getAllForOneArtiste(UUID TrackingIdArtiste) ;

    List<AlbumResponse> listeAlbums();

    AlbumResponse update(UUID trakingId, AlbumRequest request);


}

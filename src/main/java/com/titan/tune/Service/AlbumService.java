package com.titan.tune.Service;

import com.titan.tune.Dto.Request.AlbumRequest;
import com.titan.tune.Dto.Response.AlbumResponse;

import java.util.List;
import java.util.UUID;

public interface AlbumService {
    AlbumResponse create(AlbumRequest request ,  UUID trakingId);

    AlbumResponse getAlbum(UUID trackingId);

     void delete(UUID trakingId);

    List<AlbumResponse> listeAlbums();

    AlbumResponse update(UUID trakingId, AlbumRequest request);


}

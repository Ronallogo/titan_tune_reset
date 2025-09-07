package com.titan.tune.Service;

import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Request.ClientsRequest;
import com.titan.tune.Dto.Request.LoginRequest;
import com.titan.tune.Dto.Response.ArtistResponse;
import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Dto.Response.UserAuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface UserService {
    UserAuthenticationResponse authenticate(LoginRequest loginDTO);
    ArtistResponse createArtist(ArtistsRequest request);
    ArtistResponse updateArtistEtat(UUID trackingId, boolean etat);
    ClientResponse inscriptionClient(ClientsRequest clientRequest);
}

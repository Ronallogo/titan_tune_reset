package com.titan.tune.Service;

import com.titan.tune.Dto.Request.AdminRequest;
import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Request.ClientsRequest;
import com.titan.tune.Dto.Request.LoginRequest;
import com.titan.tune.Dto.Response.*;
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

    AdminResponse inscriptionAdmin(AdminRequest request);
    UserDetails loadUserByUsername(String email) ;
    void activation(Map<String, String> activation);
    void modifierMotDePasse(Map<String, String> parametres);
    void nouveauMotDePasse(Map<String, String> parametres);

    void deleteUser(UUID trackingId);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(UUID trackingId, UserResponse response);
    UserResponse getUserByTrackingId(UUID trackingId);

}

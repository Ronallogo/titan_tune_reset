package com.titan.tune.Mapper;




import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Response.ArtistResponse;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Repositories.ArtistRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ArtistMapper {



    public Artiste toEntity(ArtistsRequest request) {


        Artiste artists = new Artiste();

        artists.setTrackingId(UUID.randomUUID());
        artists.setFirstName(request.firstName());
        artists.setLastName(request.lastName());
        artists.setAlias(request.alias());
        artists.setPhone(request.phone());
        artists.setEmail(request.email());
        artists.setPassword(request.password());
        artists.setDescription(request.description());

        return artists;
    }


    public ArtistResponse toResponse(Artiste artists) {


        return ArtistResponse.builder()
                .trackingId(artists.getTrackingId())
                .FirstName(artists.getFirstName())
                .LastName(artists.getLastName())
                .Alias(artists.getAlias())
                .Phone(artists.getPhone())
                .Email(artists.getEmail())
                .Password(artists.getPassword())
                .description(artists.getDescription())
                .build();
    }


    public Artiste updateArtist(
            ArtistsRequest request ,
            Artiste  artiste
    ) {



        artiste.setFirstName(request.firstName());
        artiste.setLastName(request.lastName());
        artiste.setAlias(request.alias());
        artiste.setPhone(request.phone());
        artiste.setEmail(request.email());
        artiste.setPassword(request.password());
        artiste.setDescription(request.description());



        return  artiste  ;
    }






}
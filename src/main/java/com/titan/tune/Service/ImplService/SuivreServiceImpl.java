package com.titan.tune.Service.ImplService;


import com.titan.tune.Dto.Response.SuivreResponse;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.Suivre;
import com.titan.tune.Entity.User;
import com.titan.tune.Mapper.SuivreMapper;
import com.titan.tune.Repositories.SuivreRepository;
import com.titan.tune.Repositories.UserRepository;
import com.titan.tune.Service.SuivreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SuivreServiceImpl implements SuivreService {

    private  final UserRepository userRepository ;
    private final SuivreRepository repository ;
    private final SuivreMapper mapper ;

    public SuivreServiceImpl(UserRepository userRepository, SuivreRepository repository, SuivreMapper mapper) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.mapper = mapper;
    }
    /*
     *
     * Un artiste peut suivre un autre artiste
     * Un client peut suivre un  artiste
     * Un aucun client ne dois etre suivi aussi
     *
     * */
    @Override
    public SuivreResponse follow(UUID trackingIdFollowers, UUID trackingIdFollowing) {

        if(trackingIdFollowers == null ) throw  new IllegalArgumentException("tracking id follower null");

        if(trackingIdFollowing == null) throw  new IllegalArgumentException("tracking id following null");

        var follower = this.userRepository.findByTrackingId(trackingIdFollowers)
                .orElseThrow(()-> new RuntimeException("client introuvable")) ;

        var   following  = this.userRepository.findArtist(trackingIdFollowing)
                .orElseThrow(()-> new RuntimeException(" artiste introuvable ou trackingId invalide"));


        var suivre = this.mapper.toEntity(follower , following) ;

        suivre.setTrackingId(UUID.randomUUID());

        suivre =  this.repository.save(suivre) ;

        return this.mapper.toResponse(suivre);

    }

    @Override
    public Integer nbrFollowers(UUID trackingIdArtiste) {
        var artiste  = this.userRepository.findArtist(trackingIdArtiste)
                .orElseThrow(()-> new RuntimeException(" artiste introuvable"));
        return   this.repository.nbrFollowers(artiste.getId());
    }

    @Override
    public Integer nbrFollowing(UUID trackingIdUser) {

        if(trackingIdUser == null) throw new RuntimeException("trackingId est null" );

        var  user = this.userRepository.findClient(trackingIdUser)
                .orElseThrow(()-> new RuntimeException("client introuvable")) ;
        return this.repository.nbrFollowing( user.getId());
    }

    @Override
    public void unfollow(UUID trackingIdFollower, UUID trackingIdFollowing) {

        if(trackingIdFollower == null ) throw new RuntimeException("trackingIdFollower est null") ;

        if(trackingIdFollowing == null) throw  new RuntimeException("trackingIdFollowing est null");

        User follower = this.userRepository.findByTrackingId(trackingIdFollower)
                .orElseThrow(()-> new IllegalArgumentException("tracking id follower invalide ou follower introuvable")) ;

        var artiste = this.userRepository.findArtist(trackingIdFollowing)
                .orElseThrow(()-> new IllegalArgumentException("tracking id artiste invalide ou follower introuvable")) ;

        Suivre suivre = this.repository.findByTrackingIdEmbeddable(follower.getId() ,artiste.getId())
                .orElseThrow(()-> new RuntimeException("aucune relation trouvée"));

        suivre.setFollow(false);

        this.repository.save(suivre);
    }

    @Override
    public List<SuivreResponse> followers(UUID trackingIdArtiste) {

        if(trackingIdArtiste == null) throw new RuntimeException("trackingIdArtiste est null");

        var artiste = this.userRepository.findArtist(trackingIdArtiste)
                .orElseThrow(()-> new IllegalArgumentException("tracking id artiste invalide"));

        List<Suivre> listFollowers =  this.repository.followers(artiste.getId());

        return listFollowers.stream()
                .map(this.mapper::toResponse)
                .toList() ;

    }

    @Override
    public List<SuivreResponse> following(UUID trackingIdUser) {

        var follower = this.userRepository.findByTrackingId(trackingIdUser)
                .orElseThrow(()-> new IllegalArgumentException("tracking id user invalide"));
        System.out.println(follower.getEmail());

        List<Suivre> listFollowers =  this.repository.following(follower.getId());
        return listFollowers.stream()
                .map(this.mapper::toResponse)
                .toList() ;
    }


    @Override
    public boolean checkIfRelationExist(UUID trackingIdFollower, UUID trackingIdFollowing) {
        return false;
    }
}

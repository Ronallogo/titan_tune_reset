package com.titan.tune.Mapper;

import com.titan.tune.Dto.Response.SuivreResponse;
import com.titan.tune.Entity.Suivre;
import com.titan.tune.Entity.Suivre_id;
import com.titan.tune.Entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuivreMapper {

    public SuivreResponse toResponse(Suivre suivre){

        return new SuivreResponse(
                suivre.getFollower().getTrackingId() ,
                suivre.getFollowing().getTrackingId() ,
                suivre.getFollower().getFirstName() +" "+
                        suivre.getFollower().getLastName() ,
                suivre.getFollowing().getFirstName() + " "+
                        suivre.getFollowing().getLastName()

        );
    }


    List<SuivreResponse> toResponseList(List<Suivre> list){
            return list.stream().map(this::toResponse)
                    .toList() ;
    }






    /*

     * follower : celui qui s'abonne
     * following : l'artiste auquel le follower s'abonne
     *
     * */

    public Suivre toEntity(User follower , User following){

        return new Suivre(
                new Suivre_id(follower.getId() , following.getId()) ,
                true ,
                follower ,
                following
        );
    }


}

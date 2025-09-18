package com.titan.tune.Mapper;


import com.titan.tune.Dto.Request.CategorieRequest;
import com.titan.tune.Dto.Response.CategorieResponse;
import com.titan.tune.Entity.Categorie;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CategorieMapper {





    public Categorie toEntity(CategorieRequest request) {
        Categorie categorie = new Categorie();
        categorie.setNomCategorie(request.nomCategorie());
        categorie.setTrackingId(UUID.randomUUID());

        return categorie ;

    }

    public Categorie toEntityByUpdate(CategorieRequest request  ,  Categorie entity) {

        entity.setNomCategorie(request.nomCategorie());
        return  entity ;
    }

    public CategorieResponse toResponse(Categorie entity) {
        return new CategorieResponse(
                entity.getTrackingId(),
                entity.getNomCategorie()
        );
    }




    public List<CategorieResponse> toResponseList(List<Categorie> list){
            return list.stream()
                    .map(this::toResponse)
                    .toList()  ;
    }
}

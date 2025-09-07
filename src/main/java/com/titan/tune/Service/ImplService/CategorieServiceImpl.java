package com.titan.tune.Service.ImplService;


import com.titan.tune.Dto.Request.CategorieRequest;
import com.titan.tune.Dto.Response.CategorieResponse;
import com.titan.tune.Dto.Response.SongResponse;
import com.titan.tune.Entity.Categorie;
import com.titan.tune.Entity.Song;
import com.titan.tune.Mapper.CategorieMapper;
import com.titan.tune.Repositories.CategorieRepository;
import com.titan.tune.Service.CategorieService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategorieServiceImpl implements CategorieService {
    private final CategorieMapper categorieMapper;
    private final CategorieRepository repository ;

    public CategorieServiceImpl(CategorieMapper categorieMapper, CategorieRepository repository) {
        this.categorieMapper = categorieMapper;
        this.repository = repository;
    }

    public CategorieResponse addCategorie(CategorieRequest request) {

        Assert.notNull(request , "make sure that request is not null");

        Categorie categorie = categorieMapper.toEntity(request);
        var result = this.repository.save(categorie);
        return categorieMapper.toResponse(result);
    }



    public List<CategorieResponse> listCategories() {
        return  repository.findAll()
                .stream()
                .map(categorieMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CategorieResponse updateCategorie(UUID trackingId, CategorieRequest request) {
        Assert.notNull(trackingId , "tracking id must be not null");

        Assert.notNull(request , "request must be not null");

        Categorie categorie =  repository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie not found"));

        categorie.setNomCategorie(request.nomCategorie());

        var result = this.repository.save(categorie);

        return categorieMapper.toResponse(result);
    }

    @Override
    public  CategorieResponse findByTrackingId(UUID trackingId) {
        /// vrerifie si la valeur est null
        Assert.notNull(trackingId , "this categorie tracking id is null");
        Categorie c = this.repository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie not found"));

        return this.categorieMapper.toResponse(c);
    }

    @Override
    public List<CategorieResponse> findByName(String name) {
        return this.repository.findCategorieByName(name)
                .stream().map(this.categorieMapper::toResponse)
                .toList();
    }


    @Override
    public void deleteByTrackingId(UUID trackingId) {
        repository.deleteByTrackingId(trackingId);
    }
}

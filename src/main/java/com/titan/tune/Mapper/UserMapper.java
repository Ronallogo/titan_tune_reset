package com.titan.tune.Mapper;//package nitchcorp.titan.tunes.Mapper;
//
//import nitchcorp.titan.tunes.Dto.Request.UserRequest;
//import nitchcorp.titan.tunes.Dto.Response.UserResponse;
//import nitchcorp.titan.tunes.Entity.Artiste;
//import utils.TypeRole;
//import nitchcorp.titan.tunes.Entity.User;
//import org.springframework.stereotype.Component;
//
//
//import java.util.UUID;
//
//@Component
//public class UserMapper {
//
//    public UserResponse toResponse(User user){
//        if(user == null){
//            new IllegalArgumentException("Votre response est null");
//        }
//      String description = null;
//        if(user instanceof Artiste){
//            description = ((Artiste)user).getDescription();
//        }
//     return new UserResponse(
//             user.getTrackingId(),
//             user.getNom(),
//             user.getPrenom(),
//             user.getSurnom(),
//             user.getTelephone(),
//             user.getEmail(),
//             user.getPassword(),
//             user.getRole(),
//             user.getImage(),
//             description
//     );
//    }
//
//
//
//    public User  toEntity(UserRequest request) {
//        if (request == null) {
//            throw new IllegalArgumentException("Vitre requete est null");
//        }
//        User user = new User();
//        switch (request.role()) {
//            case ADMIN:
//                user.setRole(TypeRole.ADMIN);
//                break;
//            case CLIENT:
//                user.setRole(TypeRole.CLIENT);
//                break;
//            case ARTISTE:
//                user.setRole(TypeRole.ARTISTE);
//                break;
//            default:
//                throw new IllegalArgumentException(" Ce role n'est pas reconnu : " + request.role());
//        }
//
//        user.setTrackingId(UUID.randomUUID());
//        user.setNom(request.nom());
//        user.setPrenom(request.prenom());
//        user.setSurnom(request.surnom());
//        user.setTelephone(request.telephone());
//        user.setEmail(request.email());
//        user.setPassword(request.password());
//        user.setImage(request.image());
//        if (request.description() != null) {
//            if (user instanceof Artiste) {
//                ((Artiste) user).setDescription(request.description());
//            }
//        }
//
//     return user;
//    }
//
//    public User toEntityFromResponse(UserResponse request, User user){
//        if(request == null){
//            throw new IllegalArgumentException("Votre response est null");
//        }
//        if(user == null){
//            throw new IllegalArgumentException("Votre utilisteur  est null");
//        }
//            user.setNom(request.nom());
//            user.setPrenom(request.prenom());
//            user.setSurnom(request.surnom());
//            user.setEmail(request.email());
//
//        return user;
//    }
//}

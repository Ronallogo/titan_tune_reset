package com.titan.tune.Mapper;
 
import com.titan.tune.Dto.Request.ClientsRequest;

import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Entity.Clients;
import com.titan.tune.utils.TypeRole;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientsMapper {

    public Clients toEntity(ClientsRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The request don't be null");
        }

        Clients Clients = new Clients();
        Clients.setTrackingId(UUID.randomUUID());
        Clients.setFirstName(request.firstName());
        Clients.setLastName(request.lastName());
        Clients.setEmail(request.email());
        Clients.setRole(TypeRole.CLIENT);
        Clients.setPassword(request.password());
        Clients.setPhone(request.phone());

        return Clients;
    }

    public ClientResponse toResponse(Clients Clients) {
        if (Clients == null) {
            throw new IllegalArgumentException("The Clients don't be null");
        }
        return new ClientResponse(
                Clients.getTrackingId(),
                Clients.getFirstName(),
                Clients.getLastName(),
                Clients.getEmail(),
                null,
                Clients.getPhone()
        );
    }
}
package com.titan.tune.Dto.Response;

import java.util.List;
import java.util.UUID;

public class UserAuthenticationResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String roles;
    private List<String> rolesList;
    private String token;
    private String type = "Bearer";
    private UUID trackingId;
    private boolean actif;

    public UserAuthenticationResponse(String token,
                                      Long id,
                                      String firstName,
                                      String lastName,
                                      String phone,
                                      String email,
                                      String roles,
                                      List<String> rolesList,
                                      UUID trackingId,
                                      boolean actif) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
        this.rolesList = rolesList;
        this.trackingId = trackingId;
        this.actif = actif;
    }

    // -------------------- Getters et Setters --------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
    public List<String> getRolesList() { return rolesList; }
    public void setRolesList(List<String> rolesList) { this.rolesList = rolesList; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public UUID getTrackingId() { return trackingId; }
    public void setTrackingId(UUID trackingId) { this.trackingId = trackingId; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}

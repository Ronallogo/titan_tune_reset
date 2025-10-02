package com.titan.tune.Service.ImplService;


import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.titan.tune.Dto.Request.AdminRequest;
import com.titan.tune.Dto.Request.ArtistsRequest;
import com.titan.tune.Dto.Request.ClientsRequest;
import com.titan.tune.Dto.Request.LoginRequest;
import com.titan.tune.Dto.Response.AdminResponse;
import com.titan.tune.Dto.Response.ArtistResponse;
import com.titan.tune.Dto.Response.ClientResponse;
import com.titan.tune.Dto.Response.UserAuthenticationResponse;
import com.titan.tune.Dto.Response.UserResponse;
import com.titan.tune.Entity.Artiste;
import com.titan.tune.Entity.Clients;
import com.titan.tune.Entity.User;
import com.titan.tune.Mapper.ArtistMapper;
import com.titan.tune.Mapper.ClientsMapper;
import com.titan.tune.Repositories.ArtistRepository;
import com.titan.tune.Repositories.ClientRepository;
import com.titan.tune.Repositories.UserRepository;
import com.titan.tune.Service.UserService;
import com.titan.tune.config.TunesEmailConstants;
import com.titan.tune.mailling.dto.request.EmailRequest;
import com.titan.tune.mailling.service.EmailService;
import com.titan.tune.securite.UserDetailsconf.UserDetailsImpl;
import com.titan.tune.securite.configSecurity.RandomGenerator;
import com.titan.tune.securite.jwt.JwtUtils;
import com.titan.tune.utils.PasswordGenerator;
import com.titan.tune.utils.PasswordResult;
import com.titan.tune.utils.TypeRole;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final ArtistMapper artistMapper;
    private final RandomGenerator randomGenerator;
    private final ArtistRepository artistRepository;
    private final ClientsMapper clientMapper;
    private final ClientRepository clientRepository;

    public UserServiceImpl(
        
            UserRepository userRepository,
            JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder,
            @Lazy AuthenticationManager authenticationManager, // Use @Lazy here
            EmailService emailService,
            ArtistMapper artistMapper,
            RandomGenerator randomGenerator,
            ArtistRepository artistRepository, ClientsMapper clientMapper, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.artistMapper = artistMapper;
        this.randomGenerator = randomGenerator;
        this.artistRepository = artistRepository;
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
         
    }

  /*  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserDetailsImpl.build(user);
    }*/

    @Override
    public UserAuthenticationResponse authenticate(LoginRequest loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.email(),
                            loginDTO.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> rolesList = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return new UserAuthenticationResponse(
                    token,
                    userDetails.getId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getPhone(),
                    userDetails.getEmail(),
                    userDetails.getRoles(),
                    rolesList,
                    userDetails.getTrackingId(),
                    userDetails.isActif()
            );

        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Les paramètres de connexion sont incorrects");
        }
    }

    @Override
    public  Map<String  , Object>  createArtist(ArtistsRequest request) {

        if(this.userRepository.existsByEmail(request.email())) {
            throw new  RuntimeException( "user déjà existant") ;
        } 


        Map<String , Object> response = new HashMap<>() ; 
        // Mapper la requête en entité
        Artiste artists = artistMapper.toEntity(request);

        // Définir le trackingId
        artists.setTrackingId(UUID.randomUUID());

        // Définir le rôle fixe ARTIST
        artists.setRole(TypeRole.ARTIST);

        // Définir l'état initial
        artists.setActif(false);

        // Générer et stocker le mot de passe sécurisé
        PasswordResult passwordResult = PasswordGenerator.generateSecurePassword();
        artists.setPassword(passwordResult.hashedPassword());
        var result = artistRepository.save(artists);

        scheduler.schedule(() -> {
            try {
                System.out.println("⏰ Exécution email différé pour: " +   result.getEmail());  
                EmailRequest emailRequest = buildDefaultEmailRequest(result, passwordResult.plainPassword());
                 emailService.send(emailRequest, TunesEmailConstants.SUBSCRIPTION_TEMPLATE);
            } catch (Exception e) {
                System.out.println("❌ Email échoué: " + e.getMessage());
            }
        }, 2, TimeUnit.SECONDS); // ← Délai de 2 secondes

        response.put("content",  artistMapper.toResponse(result)) ; 
        response.put("activation_code" ,  passwordResult.plainPassword()) ; 

        
        return response ;
    }

    @Override
    @Transactional
    public   ClientResponse inscriptionClient(ClientsRequest clientRequest) {

        if(this.userRepository.existsByEmail(clientRequest.email())) {
            throw new  RuntimeException( "user déjà existant") ;
        } 

        // Mapper la requête en entité
        Clients client = clientMapper.toEntity(clientRequest);

        // Générer le trackingId obligatoire
        client.setTrackingId(UUID.randomUUID());

        // Encoder le mot de passe fourni par le client
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        // Client activé immédiatement (contrairement aux artistes)
        client.setActif(true);

        // Sauvegarder le client
        Clients savedClient = clientRepository.save(client);

        return clientMapper.toResponse(savedClient);
    }

    @Override
    public AdminResponse inscriptionAdmin(AdminRequest request) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    @Override
    public void activation(Map<String, String> activation) {


    }

    @Override
    public void modifierMotDePasse(Map<String, String> parametres) {

    }

    @Override
    public void nouveauMotDePasse(Map<String, String> parametres) {

    }

    @Override
    public void deleteUser(UUID trackingId) {

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse updateUser(UUID trackingId, UserResponse response) {
        return null;
    }

    @Override
    public UserResponse getUserByTrackingId(UUID trackingId) {
        return null;
    }

    private EmailRequest buildDefaultEmailRequest(User user, String password) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return EmailRequest.builder()
                .mailFrom(TunesEmailConstants.EMAIL_FROM)
                .mailTo(user.getEmail())
                .mailCc(TunesEmailConstants.EMAIL_CC)
                .mailBcc(TunesEmailConstants.EMAIL_BCC)
                .mailSubject(TunesEmailConstants.EMAIL_SUBJECT)
                .mailContent(TunesEmailConstants.EMAIL_CONTENT)
                .entreprise(TunesEmailConstants.COMPANY_NAME)
                .contact(TunesEmailConstants.SUPPORT_EMAIL)
                .nom(user.getFirstName() + " " + user.getLastName())
                .username(user.getEmail())
                .password(password)
                .lien(TunesEmailConstants.CONFIRMATION_BASE_URL + user.getTrackingId())
                .build();
    }

    private void sendSubscriptionEmail(EmailRequest emailRequest, String template) {
        try {
            emailService.send(emailRequest, template);
        } catch (Exception e) {
            String errorMessage = "Échec de l'envoi de l'email de confirmation: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }

    @Override
    @Transactional
    public ArtistResponse updateArtistEtat(UUID trackingId, boolean etat) {
        // Récupérer l'artiste avec l'ID de suivi
        Artiste artists = artistRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Artist not found with trackingId: " + trackingId));

        // Mise à jour de l'état
        artists.setActif(etat);
        artists = artistRepository.save(artists);

        // Si l'artiste est activé (etat = true), envoyer l'email de validation de compte
        if (etat) {
            // Générer à la fois le mot de passe en clair et le mot de passe haché
            PasswordResult passwordResult = PasswordGenerator.generateSecurePassword();

            // Mettre à jour le mot de passe haché dans la base de données
            artists.setPassword(passwordResult.hashedPassword());
            artistRepository.save(artists);

            // Construire et envoyer l'email - Si ça échoue, tout échoue
            EmailRequest emailRequest = buildDefaultEmailRequest(artists, passwordResult.plainPassword());
            emailService.send(emailRequest, TunesEmailConstants.EMAIL_TEMPLATE_ACCOUNT_VALIDATION);
        }

        return artistMapper.toResponse(artists);
    }



    public List<ClientResponse> allClient(){
            return this.userRepository.findAllClient()
                    .stream().map(user ->this.clientMapper.toResponse((Clients)user))
                    .toList() ;
    }

    public List<ArtistResponse> allArtiste(){
        return this.userRepository.findAllArtist()
                .stream().map(user-> this.artistMapper.toResponse((Artiste) user))
                .toList() ;
    }



}
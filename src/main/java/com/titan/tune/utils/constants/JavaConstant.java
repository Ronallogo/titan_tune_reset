package com.titan.tune.utils.constants;

import org.springframework.beans.factory.annotation.Value;

public class JavaConstant {

    @Value("${client.address}")
    private static String FRONTEND_SERVER_URL;

    public final static String API_BASE_URL = "/api";
    public final static String FRONTEND_URL = "*";

    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token can not be verified";
    public static final String GET_ARRAYS_LLC = "Get arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "User management portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    public static final String[] PUBLIC_URLS = {
            "/user/registerArtist",
            "/user/registerClient",
            "/user/login",
            "/categorie/**" ,
            "/album/**" ,
            "/song/**" ,

            "/user/etat/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    public static final String[] URL_FOR_VALIDATE_ACCOUNTS = {
            API_BASE_URL + "/demandeur/valide/**",
            API_BASE_URL + "/offreur/valide/**"
    };
}
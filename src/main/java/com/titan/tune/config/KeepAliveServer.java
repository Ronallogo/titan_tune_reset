package com.titan.tune.config;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






@Service
public class KeepAliveServer {
    private static final Logger logger = LoggerFactory.getLogger(KeepAliveServer.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://titan-tunes-0-0-1.onrender.com/server/ping";

    @Scheduled(fixedRate = 50000)
    public void pingServer() {
        try {
            String response = restTemplate.getForObject(url, String.class);
            logger.info("Ping réussi à {}: {}", url, response);
        } catch (Exception e) {
            logger.error("Erreur lors du ping du serveur: {}", e.getMessage());
        }
    }
}

package com.titan.tune.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private Bucket bucket = new Bucket();

    @Data
    public static class Bucket {
        private String songs = "titan-songs";
        private String images = "titan-images";
        private String videos = "titan-videos";
        private String photos = "titan-photos";
        
        public String getSongs() {
            return songs;
        }
        
        public String getImages() {
            return images;
        }
        
        public String getVideos() {
            return videos;
        }
        
        public String getPhotos() {
            return photos;
        }
    }

    public String getEndpoint() {
        return url;
    }

    public String getAccessKey() {
        return accessKey != null ? accessKey : "minioadmin";
    }

    public String getSecretKey() {
        return secretKey != null ? secretKey : "minioadmin123";
    }
    
    public Bucket getBucket() {
        return bucket;
    }

    public String getUrl() {
        return url != null ? url : "http://localhost:9000";
    }
}

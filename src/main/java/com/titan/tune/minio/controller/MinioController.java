package com.titan.tune.minio.controller;

import com.titan.tune.minio.dto.FileDownloadResponse;
import com.titan.tune.minio.dto.FileMetadata;
import com.titan.tune.minio.dto.FileUploadResponse;
import com.titan.tune.minio.enums.FileType;
import com.titan.tune.minio.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File Management", description = "API pour la gestion des fichiers multimédias avec MinIO")
public class MinioController {

    private final MinioService minioService;

    @PostMapping(value = "/upload/{fileType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload un fichier", description = "Upload un fichier vers MinIO selon le type spécifié")
    @ApiResponse(responseCode = "200", description = "Fichier uploadé avec succès")
    @ApiResponse(responseCode = "400", description = "Type de fichier invalide ou fichier corrompu")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @Parameter(description = "Type de fichier (SONG, IMAGE, VIDEO, PHOTO)", required = true)
            @PathVariable FileType fileType,
            @Parameter(description = "Fichier à uploader", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Nom personnalisé pour le fichier (optionnel)")
            @RequestParam(value = "customFileName", required = false) String customFileName) {
        
        log.info("Uploading file: {} of type: {}", file.getOriginalFilename(), fileType);
        FileUploadResponse response = minioService.uploadFile(file, fileType, customFileName);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping(value = "/upload/multiple/{fileType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload plusieurs fichiers", description = "Upload plusieurs fichiers vers MinIO selon le type spécifié")
    @ApiResponse(responseCode = "200", description = "Fichiers uploadés avec succès")
    @ApiResponse(responseCode = "400", description = "Type de fichier invalide ou fichiers corrompus")
    public ResponseEntity<List<FileUploadResponse>> uploadMultipleFiles(
            @Parameter(description = "Type de fichier (SONG, IMAGE, VIDEO, PHOTO)", required = true, example = "IMAGE")
            @PathVariable FileType fileType,
            @Parameter(description = "Fichiers à uploader", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        
        log.info("Uploading {} files of type: {}", files.size(), fileType);
        List<FileUploadResponse> responses = minioService.uploadMultipleFiles(files, fileType);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/download/{fileType}/{fileName}")
    @Operation(summary = "Télécharger un fichier", description = "Télécharge un fichier depuis MinIO")
    @ApiResponse(responseCode = "200", description = "Fichier téléchargé avec succès")
    @ApiResponse(responseCode = "404", description = "Fichier non trouvé")
    public ResponseEntity<InputStreamResource> downloadFile(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Downloading file: {} of type: {}", fileName, fileType);
        FileDownloadResponse response = minioService.downloadFile(fileName, fileType);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFileName() + "\"")
                .contentLength(response.getFileSize())
                .body(new InputStreamResource(response.getInputStream()));
    }

    @GetMapping("/stream/{fileType}/{fileName}")
    @Operation(summary = "Stream un fichier", description = "Stream un fichier depuis MinIO (pour lecture directe)")
    @ApiResponse(responseCode = "200", description = "Fichier streamé avec succès")
    @ApiResponse(responseCode = "404", description = "Fichier non trouvé")
    public ResponseEntity<InputStreamResource> streamFile(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Streaming file: {} of type: {}", fileName, fileType);
        FileDownloadResponse response = minioService.downloadFile(fileName, fileType);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + response.getFileName() + "\"")
                .contentLength(response.getFileSize())
                .body(new InputStreamResource(response.getInputStream()));
    }

    @DeleteMapping("/{fileType}/{fileName}")
    @Operation(summary = "Supprimer un fichier", description = "Supprime un fichier de MinIO")
    @ApiResponse(responseCode = "200", description = "Fichier supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Fichier non trouvé")
    public ResponseEntity<String> deleteFile(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Deleting file: {} of type: {}", fileName, fileType);
        boolean deleted = minioService.deleteFile(fileName, fileType);
        
        if (deleted) {
            return ResponseEntity.ok("Fichier supprimé avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/metadata/{fileType}/{fileName}")
    @Operation(summary = "Obtenir les métadonnées d'un fichier", description = "Récupère les métadonnées d'un fichier")
    @ApiResponse(responseCode = "200", description = "Métadonnées récupérées avec succès")
    @ApiResponse(responseCode = "404", description = "Fichier non trouvé")
    public ResponseEntity<FileMetadata> getFileMetadata(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Getting metadata for file: {} of type: {}", fileName, fileType);
        FileMetadata metadata = minioService.getFileMetadata(fileName, fileType);
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/list/{fileType}")
    @Operation(summary = "Lister les fichiers", description = "Liste tous les fichiers d'un type donné")
    @ApiResponse(responseCode = "200", description = "Liste des fichiers récupérée avec succès")
    public ResponseEntity<List<FileMetadata>> listFiles(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType) {
        
        log.info("Listing files of type: {}", fileType);
        List<FileMetadata> files = minioService.listFiles(fileType);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/exists/{fileType}/{fileName}")
    @Operation(summary = "Vérifier l'existence d'un fichier", description = "Vérifie si un fichier existe")
    @ApiResponse(responseCode = "200", description = "Statut d'existence retourné")
    public ResponseEntity<Boolean> fileExists(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Checking existence of file: {} of type: {}", fileName, fileType);
        boolean exists = minioService.fileExists(fileName, fileType);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/url/{fileType}/{fileName}")
    @Operation(summary = "Obtenir l'URL d'un fichier", description = "Récupère l'URL directe d'un fichier")
    @ApiResponse(responseCode = "200", description = "URL récupérée avec succès")
    public ResponseEntity<String> getFileUrl(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName) {
        
        log.info("Getting URL for file: {} of type: {}", fileName, fileType);
        String url = minioService.getFileUrl(fileName, fileType);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/presigned-url/{fileType}/{fileName}")
    @Operation(summary = "Obtenir une URL présignée", description = "Génère une URL présignée temporaire pour un fichier")
    @ApiResponse(responseCode = "200", description = "URL présignée générée avec succès")
    public ResponseEntity<String> getPresignedUrl(
            @Parameter(description = "Type de fichier")
            @PathVariable FileType fileType,
            @Parameter(description = "Nom du fichier")
            @PathVariable String fileName,
            @Parameter(description = "Durée d'expiration en minutes (défaut: 60)")
            @RequestParam(value = "expiryMinutes", defaultValue = "60") int expiryMinutes) {
        
        log.info("Generating presigned URL for file: {} of type: {}, expiry: {} minutes", fileName, fileType, expiryMinutes);
        String presignedUrl = minioService.getPresignedUrl(fileName, fileType, expiryMinutes);
        return ResponseEntity.ok(presignedUrl);
    }

}

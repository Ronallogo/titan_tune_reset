package com.titan.tune.minio.service;

import com.titan.tune.minio.dto.FileDownloadResponse;
import com.titan.tune.minio.dto.FileMetadata;
import com.titan.tune.minio.dto.FileUploadResponse;
import com.titan.tune.minio.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioService {
    
    /**
     * Upload a file to MinIO
     */
    FileUploadResponse uploadFile(MultipartFile file, FileType fileType);
    
    /**
     * Upload a file to MinIO with custom filename
     */
    FileUploadResponse uploadFile(MultipartFile file, FileType fileType, String customFileName);
    
    /**
     * Download a file from MinIO
     */
    FileDownloadResponse downloadFile(String fileName, FileType fileType);
    
    /**
     * Delete a file from MinIO
     */
    boolean deleteFile(String fileName, FileType fileType);
    
    /**
     * Get file metadata
     */
    FileMetadata getFileMetadata(String fileName, FileType fileType);
    
    /**
     * List all files in a bucket
     */
    List<FileMetadata> listFiles(FileType fileType);
    
    /**
     * Check if file exists
     */
    boolean fileExists(String fileName, FileType fileType);
    
    /**
     * Get file URL for direct access
     */
    String getFileUrl(String fileName, FileType fileType);
    
    /**
     * Upload multiple files to MinIO
     */
    List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> files, FileType fileType);
    
    /**
     * Get presigned URL for temporary access
     */
    String getPresignedUrl(String fileName, FileType fileType, int expiryInMinutes);
}

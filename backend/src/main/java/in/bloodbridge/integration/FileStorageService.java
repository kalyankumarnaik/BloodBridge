package in.bloodbridge.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);


    private final Path storageDirectory;

    public FileStorageService(@Value("${bloodbridge.storage.upload-dir:./uploads/documents}") String uploadDir) {
        this.storageDirectory = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageDirectory);
        } catch (IOException e) {
            log.error("Could not create upload storage directory", e);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("Failed to store empty file");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            throw new BadRequestException("File size exceeds 5MB limit");
        }

        validateMagicBytes(file);

        String storageKey = UUID.randomUUID().toString();
        try {
            Path targetLocation = this.storageDirectory.resolve(storageKey);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return storageKey;
        } catch (IOException e) {
            throw new BadRequestException("Could not store file: " + e.getMessage());
        }
    }

    private void validateMagicBytes(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[8];
            int read = is.read(header);
            if (read < 4) {
                throw new BadRequestException("Invalid or corrupted file content");
            }

            // PDF check: %PDF (0x25, 0x50, 0x44, 0x46)
            boolean isPdf = header[0] == 0x25 && header[1] == 0x50 && header[2] == 0x44 && header[3] == 0x46;
            // JPEG check: 0xFF, 0xD8, 0xFF
            boolean isJpeg = (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
            // PNG check: 0x89, 0x50, 0x4E, 0x47
            boolean isPng = (header[0] & 0xFF) == 0x89 && header[1] == 0x50 && header[2] == 0x4E && header[3] == 0x47;

            if (!isPdf && !isJpeg && !isPng) {
                throw new BadRequestException("File format not allowed. Only PDF, JPG, or PNG files are accepted.");
            }
        } catch (IOException e) {
            throw new BadRequestException("Failed to verify file integrity");
        }
    }
}

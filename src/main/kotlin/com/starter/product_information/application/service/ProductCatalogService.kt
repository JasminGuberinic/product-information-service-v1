package com.starter.product_information.application.service

import com.starter.product_information.application.events.FileUploadedEvent
import com.starter.product_information.domain.model.file.FileMetadata
import com.starter.product_information.domain.model.file.FileStorageResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ProductCatalogService(
    @Value("\${app.file-storage.location:uploads}")
    private val storageLocation: String,
    private val eventPublisher: ApplicationEventPublisher,
) {
    private val rootLocation: Path = Paths.get(storageLocation)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")

    init {
        createStorageDirectoryIfNotExists()
    }

    fun uploadFile(file: MultipartFile): FileStorageResult {
        return try {
            validateFile(file)
            val savedFile = saveFile(file)
            FileStorageResult.Success(savedFile)
        } catch (e: Exception) {
            FileStorageResult.Error("Failed to upload file: ${e.message}")
        }
    }

    fun downloadFile(filename: String): Resource? {
        return try {
            val file = rootLocation.resolve(filename)
            val resource = UrlResource(file.toUri())

            if (resource.exists() || resource.isReadable) {
                resource
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun createStorageDirectoryIfNotExists() {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation)
        }
    }

    private fun validateFile(file: MultipartFile) {
        require(!file.isEmpty) { "File cannot be empty" }
        require(file.originalFilename != null) { "Filename cannot be null" }
    }

    private fun saveFile(file: MultipartFile): FileMetadata {
        val timestamp = LocalDateTime.now().format(dateFormatter)
        val originalFilename = file.originalFilename!!
        val uniqueFilename = "${timestamp}-${originalFilename}"

        val destinationFile = rootLocation.resolve(uniqueFilename)
            .normalize()
            .toAbsolutePath()

        require(destinationFile.parent == rootLocation.toAbsolutePath()) {
            "Cannot store file outside current directory"
        }

        file.inputStream.use { inputStream ->
            Files.copy(
                inputStream,
                destinationFile,
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        val metadata = FileMetadata(
            filename = uniqueFilename,
            size = file.size,
            contentType = file.contentType ?: "application/octet-stream"
        )

        eventPublisher.publishEvent(
            FileUploadedEvent(
                filename = metadata.filename,
                contentType = metadata.contentType
            )
        )

        return metadata
    }
}
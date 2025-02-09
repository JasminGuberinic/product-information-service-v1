package com.starter.product_information.web.controller

import com.starter.product_information.application.service.ProductCatalogService
import com.starter.product_information.domain.model.file.FileStorageResult
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/files")
class ProductCatalogController(
    private val productCatalogService: ProductCatalogService
) {
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        return when (val result = productCatalogService.uploadFile(file)) {
            is FileStorageResult.Success -> {
                ResponseEntity.ok(mapOf(
                    "message" to "File uploaded successfully",
                    "filename" to result.metadata.filename,
                    "size" to result.metadata.size,
                    "contentType" to result.metadata.contentType
                ))
            }
            is FileStorageResult.Error -> {
                ResponseEntity.badRequest().body(mapOf(
                    "error" to result.message
                ))
            }
        }
    }

    @GetMapping("/download/{filename}")
    fun downloadFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val resource = productCatalogService.downloadFile(filename)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${filename}\""
            )
            .body(resource)
    }
}
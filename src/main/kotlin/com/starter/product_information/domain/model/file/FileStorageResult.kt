package com.starter.product_information.domain.model.file

sealed class FileStorageResult {
    data class Success(val metadata: FileMetadata) : FileStorageResult()
    data class Error(val message: String) : FileStorageResult()
}
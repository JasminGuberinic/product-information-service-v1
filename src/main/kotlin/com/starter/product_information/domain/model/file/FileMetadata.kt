package com.starter.product_information.domain.model.file

data class FileMetadata(
    val filename: String,
    val size: Long,
    val contentType: String
)
package com.starter.product_information.application.events

import java.time.LocalDateTime

data class FileUploadedEvent(
    val filename: String,
    val contentType: String,
    val uploadedAt: LocalDateTime = LocalDateTime.now()
)
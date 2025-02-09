package com.starter.product_information.web.dto

data class RecordInteractionDto(
    val userId: Long,
    val productId: Long,
    val interactionType: String,
    val rating: Int?
)

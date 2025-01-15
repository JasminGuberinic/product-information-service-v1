package com.starter.product_information.application.events

data class ProductCreatedEvent(
    val productId: Long,
    val name: String,
    val description: String,
    val price: Double
)
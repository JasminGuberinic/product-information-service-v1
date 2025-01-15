package com.starter.product_information.application.commands.create_product

data class CreateProductCommand(
    val name: String,
    val description: String,
    val price: Double
)
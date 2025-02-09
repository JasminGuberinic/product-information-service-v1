package com.starter.product_information.application.graphql.input

data class ProductPriceInput(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
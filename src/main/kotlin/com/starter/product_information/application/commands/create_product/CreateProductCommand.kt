package com.starter.product_information.application.commands.create_product

import com.starter.product_information.web.dto.ProductDto

data class CreateProductCommand(
    val productDto: ProductDto
)
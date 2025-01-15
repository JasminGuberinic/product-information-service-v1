package com.starter.product_information.application.query.get_product

import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.web.dto.ProductDto
import org.springframework.stereotype.Component

@Component
class GetProductQueryHandler(
    private val productRepository: ProductRepository
) {

    fun handle(query: GetProductQuery): ProductDto? {
        val product = productRepository.findById(query.productId).orElse(null)
        return product?.let {
            ProductDto(it.name, it.description, it.price)
        }
    }
}
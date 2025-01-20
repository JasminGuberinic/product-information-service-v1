package com.starter.product_information.domain.services

import com.starter.product_information.domain.entities.product.Product
import org.springframework.stereotype.Service

@Service
class ProductDomainService {
    fun validateProduct(product: Product) {
        require(product.name.isNotBlank()) { "Product name cannot be blank" }
        require(product.price > 0) { "Product price must be greater than zero" }
        require(product.description.isNotBlank()) { "Product description cannot be blank" }
        require(product.productionQuantity > 0) { "Production quantity must be greater than zero" }
    }
}
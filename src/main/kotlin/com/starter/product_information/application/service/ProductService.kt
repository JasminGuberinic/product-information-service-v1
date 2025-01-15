package com.starter.product_information.application.service

import com.starter.product_information.infrastructure.entities.Product
import com.starter.product_information.infrastructure.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun createProduct(name: String, description: String, price: Double): Product {
        val product = Product(name = name, description = description, price = price)
        return productRepository.save(product)
    }
}
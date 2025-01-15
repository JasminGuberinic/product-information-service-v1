package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.infrastructure.entities.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(name: String): Product?
}
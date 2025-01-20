package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.infrastructure.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findByName(name: String): ProductEntity?
}
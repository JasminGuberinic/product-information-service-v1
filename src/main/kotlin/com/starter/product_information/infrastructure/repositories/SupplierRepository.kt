package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.infrastructure.entities.SupplierEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<SupplierEntity, Long> {
    fun findByName(name: String): SupplierEntity?
}
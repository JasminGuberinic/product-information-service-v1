package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManufacturerRepository : JpaRepository<ManufacturerEntity, Long> {
    fun findByNameAndCountry(name: String, country: String): ManufacturerEntity?
    fun findByManufacturerType(manufacturerType: ManufacturerType): List<ManufacturerEntity>
}
package com.starter.product_information.application.service

import com.starter.product_information.domain.entities.manufacturer.CustomManufacturer
import com.starter.product_information.domain.entities.manufacturer.Manufacturer
import com.starter.product_information.domain.entities.manufacturer.ManufacturerType
import com.starter.product_information.domain.entities.manufacturer.OEMManufacturer
import com.starter.product_information.domain.entities.product.Product
import com.starter.product_information.domain.services.ManufacturerDomainService
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.repositories.ManufacturerRepository
import org.springframework.stereotype.Service

@Service
class ManufacturerService(
    private val manufacturerRepository: ManufacturerRepository,
    private val manufacturerDomainService: ManufacturerDomainService
) {
    private fun createManufacturer(product: Product): ManufacturerEntity {
        val manufacturerType = manufacturerDomainService.determineManufacturerType(product)
        val manufacturerEntity = when (manufacturerType) {
            ManufacturerType.OEM -> ManufacturerEntity(
                name = product.manufacturer.name,
                country = product.manufacturer.country,
                manufacturerType = manufacturerType,
                oemCode = (product.manufacturer as OEMManufacturer).oemCode
            )
            ManufacturerType.CUSTOM -> ManufacturerEntity(
                name = product.manufacturer.name,
                country = product.manufacturer.country,
                manufacturerType = manufacturerType,
                customizationOptions = (product.manufacturer as CustomManufacturer).customizationOptions
            )
            else -> throw IllegalArgumentException("Unsupported manufacturer type")
        }
        return manufacturerRepository.save(manufacturerEntity)
    }

    fun findManufacturerByNameAndCountry(name: String, country: String): ManufacturerEntity? {
        return manufacturerRepository.findByNameAndCountry(name, country)
    }

    fun findOrCreateManufacturer(product: Product): ManufacturerEntity {
        val existingManufacturerEntity = findManufacturerByNameAndCountry(product.manufacturer.name, product.manufacturer.country)
        val manufacturerEntity = existingManufacturerEntity ?: createManufacturer(product)
        return manufacturerEntity
    }
}
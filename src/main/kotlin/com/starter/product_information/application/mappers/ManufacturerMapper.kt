package com.starter.product_information.application.mappers

import com.starter.product_information.domain.model.manufacturer.CustomManufacturer
import com.starter.product_information.domain.model.manufacturer.Manufacturer
import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import org.springframework.stereotype.Component

@Component
class ManufacturerMapper {
    fun toDomain(entity: ManufacturerEntity): Manufacturer {
        return when (entity.manufacturerType) {
            ManufacturerType.OEM -> OEMManufacturer(
                name = entity.name,
                country = entity.country,
                oemCode = "OEM123" // Example code, replace with actual logic
            )
            ManufacturerType.CUSTOM -> CustomManufacturer(
                name = entity.name,
                country = entity.country,
                customizationOptions = listOf("Option1", "Option2") // Example options, replace with actual logic
            )
            else -> throw IllegalArgumentException("Unknown manufacturer type")
        }
    }
}
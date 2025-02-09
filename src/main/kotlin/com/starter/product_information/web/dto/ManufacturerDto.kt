package com.starter.product_information.web.dto

import com.starter.product_information.domain.model.manufacturer.CustomManufacturer
import com.starter.product_information.domain.model.manufacturer.Manufacturer
import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer


data class ManufacturerDto(
    val type: ManufacturerType,
    val name: String,
    val country: String,
    val oemCode: String? = null,
    val customizationOptions: List<String>? = null
) {
    fun toDomain(): Manufacturer {
        return when (type) {
            ManufacturerType.OEM -> OEMManufacturer(
                name = name,
                country = country,
                oemCode = oemCode!!
            )
            ManufacturerType.CUSTOM -> CustomManufacturer(
                name = name,
                country = country,
                customizationOptions = customizationOptions!!
            )

            ManufacturerType.CUSTOM_HIGH_END -> TODO()
            ManufacturerType.UNKNOWN -> TODO()
        }
    }
}

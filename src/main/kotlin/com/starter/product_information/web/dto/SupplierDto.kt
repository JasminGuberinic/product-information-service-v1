package com.starter.product_information.web.dto

import com.starter.product_information.domain.model.supplier.*

data class SupplierDto(
    val type: SupplierType,
    val name: String,
    val qualityRating: Int,
    val averagePrice: Double,
    val averageDeliveryTime: Int,
    val averageOrderQuantity: Int,
    val isCertified: Boolean,
    val supplierClassification: SupplierClassification,
    val localContactInfo: LocalContactInfoDto? = null,
    val internationalContactInfo: InternationalContactInfoDto? = null
) {
    fun toDomain(): Supplier {
        return when (type) {
            SupplierType.LOCAL -> LocalSupplier(
                name = name,
                qualityRating = qualityRating,
                averagePrice = averagePrice,
                averageDeliveryTime = averageDeliveryTime,
                averageOrderQuantity = averageOrderQuantity,
                isCertified = isCertified,
                supplierClassification = supplierClassification,
                contactInfo = localContactInfo!!.toDomain()
            )
            SupplierType.INTERNATIONAL -> InternationalSupplier(
                name = name,
                qualityRating = qualityRating,
                averagePrice = averagePrice,
                averageDeliveryTime = averageDeliveryTime,
                averageOrderQuantity = averageOrderQuantity,
                isCertified = isCertified,
                supplierClassification = supplierClassification,
                contactInfo = internationalContactInfo!!.toDomain(),
                countryOfOrigin = internationalContactInfo.countryOfOrigin
            )

            SupplierType.UNKNOWN -> TODO()
        }
    }
}
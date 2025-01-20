package com.starter.product_information.application.mappers

import com.starter.product_information.domain.entities.contactInformation.InternationalContactInfo
import com.starter.product_information.domain.entities.contactInformation.LocalContactInfo
import com.starter.product_information.domain.entities.supplier.InternationalSupplier
import com.starter.product_information.domain.entities.supplier.LocalSupplier
import com.starter.product_information.domain.entities.supplier.Supplier
import com.starter.product_information.domain.entities.supplier.SupplierType
import com.starter.product_information.infrastructure.entities.SupplierEntity
import org.springframework.stereotype.Component

@Component
class SupplierMapper {
    fun toDomain(entity: SupplierEntity): Supplier {
        return when (entity.supplierType) {
            SupplierType.LOCAL -> LocalSupplier(
                name = entity.name,
                qualityRating = entity.qualityRating,
                averagePrice = entity.averagePrice,
                averageDeliveryTime = entity.averageDeliveryTime,
                averageOrderQuantity = entity.averageOrderQuantity,
                isCertified = entity.isCertified,
                supplierClassification = entity.supplierClassification,
                contactInfo = LocalContactInfo(
                    phoneNumber = entity.localContactInfo?.phoneNumber ?: "",
                    email = entity.localContactInfo?.email ?: "",
                    address = entity.localContactInfo?.address ?: ""
                )
            )
            SupplierType.INTERNATIONAL -> InternationalSupplier(
                name = entity.name,
                qualityRating = entity.qualityRating,
                averagePrice = entity.averagePrice,
                averageDeliveryTime = entity.averageDeliveryTime,
                averageOrderQuantity = entity.averageOrderQuantity,
                isCertified = entity.isCertified,
                supplierClassification = entity.supplierClassification,
                contactInfo = InternationalContactInfo(
                    phoneNumber = entity.internationalContactInfo?.phoneNumber ?: "",
                    email = entity.internationalContactInfo?.email ?: "",
                    internationalAddress = entity.internationalContactInfo?.internationalAddress ?: "",
                    timeZone = entity.internationalContactInfo?.timeZone ?: ""
                ),
                countryOfOrigin = entity.countryOfOrigin ?: ""
            )
            else -> throw IllegalArgumentException("Unknown supplier type")
        }
    }
}
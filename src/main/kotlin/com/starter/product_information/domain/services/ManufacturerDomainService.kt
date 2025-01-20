package com.starter.product_information.domain.services

import com.starter.product_information.domain.entities.manufacturer.ManufacturerType
import com.starter.product_information.domain.entities.product.DistributionChannel
import com.starter.product_information.domain.entities.product.Product
import org.springframework.stereotype.Service

@Service
class ManufacturerDomainService {
    fun determineManufacturerType(product: Product): ManufacturerType {
        return when {
            product.design == "original" && !product.customization && product.productionQuantity > 1000 && product.brand != "custom" -> ManufacturerType.OEM
            product.customization && product.price > 10000 && product.distributionChannel == DistributionChannel.DIRECT -> ManufacturerType.CUSTOM_HIGH_END
            else -> ManufacturerType.CUSTOM
        }
    }
}
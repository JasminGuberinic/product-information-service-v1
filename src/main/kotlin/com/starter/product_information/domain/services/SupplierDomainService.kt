package com.starter.product_information.domain.services

import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.Supplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import org.springframework.stereotype.Service

@Service
class SupplierDomainService {
    fun determineSupplierType(product: Product): SupplierType {
        return when {
            product.countryOfOrigin == "China" && product.productionQuantity > 10000 -> SupplierType.INTERNATIONAL
            product.certificates.contains("ISO9001") && product.price > 5000 -> SupplierType.INTERNATIONAL
            product.deliveryTime < 7 && product.countryOfOrigin == product.companyCountry -> SupplierType.LOCAL
            else -> SupplierType.UNKNOWN
        }
    }

    fun classifySupplier(supplier: Supplier): SupplierClassification {
        return when {
            supplier.qualityRating >= 4 && supplier.averagePrice > 100 && supplier.averageDeliveryTime < 10 && supplier.averageOrderQuantity > 1000 && supplier.isCertified -> SupplierClassification.Premium
            supplier.qualityRating in 3..4 && supplier.averageDeliveryTime in 10..20 -> SupplierClassification.Standard
            supplier.averagePrice < 50 && supplier.averageDeliveryTime > 20 && supplier.averageOrderQuantity > 5000 -> SupplierClassification.LowCost
            else -> SupplierClassification.Other
        }
    }
}
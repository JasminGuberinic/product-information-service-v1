package com.starter.product_information.application.service

import com.starter.product_information.application.mappers.SupplierMapper
import com.starter.product_information.domain.entities.product.Product
import com.starter.product_information.domain.entities.supplier.*
import com.starter.product_information.domain.services.SupplierDomainService
import com.starter.product_information.infrastructure.entities.*
import com.starter.product_information.infrastructure.repositories.SupplierRepository
import org.springframework.stereotype.Service

@Service
class SupplierService(
    private val supplierRepository: SupplierRepository,
    private val supplierDomainService: SupplierDomainService
) {

    fun createSupplier(product: Product): SupplierEntity {
        val supplierType = supplierDomainService.determineSupplierType(product)

        val supplierEntity = supplierEntityBuilder(product.supplier, supplierType) {
            when (supplierType) {
                SupplierType.LOCAL -> {
                    localContactInfo = LocalContactInfoEntity(
                        phoneNumber = (product.supplier as LocalSupplier).contactInfo.phoneNumber,
                        email = product.supplier.contactInfo.email,
                        address = product.supplier.contactInfo.address
                    )
                }
                SupplierType.INTERNATIONAL -> {
                    internationalContactInfo = InternationalContactInfoEntity(
                        phoneNumber = (product.supplier as InternationalSupplier).contactInfo.phoneNumber,
                        email = product.supplier.contactInfo.email,
                        internationalAddress = product.supplier.contactInfo.internationalAddress,
                        timeZone = product.supplier.contactInfo.timeZone
                    )
                    countryOfOrigin = product.supplier.countryOfOrigin
                }
                else -> throw IllegalArgumentException("Unsupported supplier type")
            }
        }

        return supplierRepository.save(supplierEntity)
    }

    private fun supplierEntityBuilder(supplier: Supplier, supplierType: SupplierType, init: SupplierEntity.() -> Unit): SupplierEntity {
        return SupplierEntity(
            name = supplier.name,
            supplierType = supplierType,
            supplierClassification = supplier.supplierClassification,
            qualityRating = supplier.qualityRating,
            averagePrice = supplier.averagePrice,
            averageDeliveryTime = supplier.averageDeliveryTime,
            averageOrderQuantity = supplier.averageOrderQuantity,
            isCertified = supplier.isCertified
        ).apply(init)
    }
}
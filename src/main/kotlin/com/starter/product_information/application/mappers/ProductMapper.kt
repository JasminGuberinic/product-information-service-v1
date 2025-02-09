package com.starter.product_information.application.mappers

import com.starter.product_information.domain.model.manufacturer.CustomManufacturer
import com.starter.product_information.domain.model.manufacturer.Manufacturer
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.Supplier
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.entities.SupplierEntity
import org.springframework.stereotype.Component

@Component
class ProductMapper {

    fun toEntity(product: Product, supplier: SupplierEntity, manufacturer: ManufacturerEntity): ProductEntity {
        return ProductEntity(
            name = product.name,
            description = product.description,
            price = product.price,
            supplier = supplier,
            manufacturer = manufacturer,
            design = product.design,
            customization = product.customization,
            productionQuantity = product.productionQuantity,
            brand = product.brand,
            distributionChannel = product.distributionChannel,
            countryOfOrigin = product.countryOfOrigin,
            certificates = product.certificates,
            deliveryTime = product.deliveryTime
        )
    }

    fun toEntity(product: Product, supplier: Supplier, manufacturer: Manufacturer): ProductEntity {
        return ProductEntity(
            name = product.name,
            description = product.description,
            price = product.price,
            supplier = mapSupplierToEntity(supplier),
            manufacturer = mapManufacturerToEntity(manufacturer),
            design = product.design,
            customization = product.customization,
            productionQuantity = product.productionQuantity,
            brand = product.brand,
            distributionChannel = product.distributionChannel,
            countryOfOrigin = product.countryOfOrigin,
            certificates = product.certificates,
            deliveryTime = product.deliveryTime
        )
    }

    private fun mapSupplierToEntity(supplier: Supplier): SupplierEntity {
        return SupplierEntity(
            name = supplier.name,
            supplierType = supplier.supplierType,
            supplierClassification = supplier.supplierClassification,
            qualityRating = supplier.qualityRating,
            averagePrice = supplier.averagePrice,
            averageDeliveryTime = supplier.averageDeliveryTime,
            averageOrderQuantity = supplier.averageOrderQuantity,
            isCertified = supplier.isCertified
        )
    }

    private fun mapManufacturerToEntity(manufacturer: Manufacturer): ManufacturerEntity {
        return when (manufacturer) {
            is OEMManufacturer -> ManufacturerEntity(
                name = manufacturer.name,
                country = manufacturer.country,
                manufacturerType = manufacturer.manufacturerType,
                oemCode = manufacturer.oemCode
            )
            is CustomManufacturer -> ManufacturerEntity(
                name = manufacturer.name,
                country = manufacturer.country,
                manufacturerType = manufacturer.manufacturerType,
                customizationOptions = manufacturer.customizationOptions
            )
            else -> throw IllegalArgumentException("Unsupported manufacturer type")
        }
    }
}
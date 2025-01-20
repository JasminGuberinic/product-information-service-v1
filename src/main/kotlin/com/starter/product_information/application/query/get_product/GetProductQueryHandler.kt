package com.starter.product_information.application.query.get_product

import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.web.dto.ProductDto
import com.starter.product_information.web.dto.SupplierDto
import com.starter.product_information.web.dto.ManufacturerDto
import org.springframework.stereotype.Component

@Component
class GetProductQueryHandler(
    private val productRepository: ProductRepository
) {

    fun handle(query: GetProductQuery): ProductDto? {
        val product = productRepository.findById(query.productId).orElse(null)
        return product?.let {
            ProductDto(
                name = it.name,
                description = it.description,
                price = it.price,
                supplier = SupplierDto(
                    name = it.supplier.name,
                    qualityRating = it.supplier.qualityRating,
                    averagePrice = it.supplier.averagePrice,
                    averageDeliveryTime = it.supplier.averageDeliveryTime,
                    averageOrderQuantity = it.supplier.averageOrderQuantity,
                    isCertified = it.supplier.isCertified,
                    supplierClassification = it.supplier.supplierClassification,
                    type = it.supplier.supplierType
                ),
                manufacturer = ManufacturerDto(
                    name = it.manufacturer.name,
                    country = it.manufacturer.country,
                    type = it.manufacturer.manufacturerType,
                ),
                design = it.design,
                customization = it.customization,
                productionQuantity = it.productionQuantity,
                brand = it.brand,
                distributionChannel = it.distributionChannel,
                countryOfOrigin = it.countryOfOrigin,
                certificates = it.certificates,
                deliveryTime = it.deliveryTime,
                companyCountry = it.companyCountry
            )
        }
    }
}
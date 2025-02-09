package com.starter.product_information.web.dto

import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.product.DistributionChannel

data class ProductDto(
    val name: String,
    val description: String,
    val price: Double,
    val supplier: SupplierDto,
    val manufacturer: ManufacturerDto,
    val design: String,
    val customization: Boolean,
    val productionQuantity: Int,
    val brand: String,
    val distributionChannel: DistributionChannel,
    val countryOfOrigin: String,
    val certificates: List<String>,
    val deliveryTime: Int,
    val companyCountry: String? = null
) {
    fun toDomain(): Product {
        return Product(
            name = this.name,
            description = this.description,
            price = this.price,
            supplier = this.supplier.toDomain(), // Ensure supplier is converted
            manufacturer = this.manufacturer.toDomain(), // Ensure manufacturer is converted
            design = this.design,
            customization = this.customization,
            productionQuantity = this.productionQuantity,
            brand = this.brand,
            distributionChannel = this.distributionChannel,
            countryOfOrigin = this.countryOfOrigin,
            certificates = this.certificates,
            deliveryTime = this.deliveryTime,
            companyCountry = this.companyCountry
        )
    }
}
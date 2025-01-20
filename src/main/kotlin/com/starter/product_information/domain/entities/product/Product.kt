package com.starter.product_information.domain.entities.product

import com.starter.product_information.domain.entities.manufacturer.Manufacturer
import com.starter.product_information.domain.entities.supplier.Supplier
import java.util.Date

class Product (
    val id: Long = 0,
    val name: String,
    val description: String,
    val supplier: Supplier,
    val manufacturer: Manufacturer,
    val design: String,
    val customization: Boolean,
    val productionQuantity: Int,
    val brand: String,
    val distributionChannel: DistributionChannel,
    val price: Double,
    val countryOfOrigin: String,
    val certificates: List<String>,
    val deliveryTime: Int,
    val companyCountry: String?
) {
    fun isSameSupplierAndManufacturer(): Boolean = supplier.name == manufacturer.name
}
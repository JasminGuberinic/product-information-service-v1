package com.starter.product_information.domain.entities.supplier

abstract class Supplier(
    val name: String,
    val qualityRating: Int,
    val averagePrice: Double,
    val averageDeliveryTime: Int,
    val averageOrderQuantity: Int,
    val isCertified: Boolean,
    val supplierClassification: SupplierClassification,
    val supplierType: SupplierType
) {
    abstract fun getSupplierType(): String
}

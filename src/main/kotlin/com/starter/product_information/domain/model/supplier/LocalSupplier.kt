package com.starter.product_information.domain.model.supplier

import com.starter.product_information.domain.model.contactInformation.LocalContactInfo

class LocalSupplier(
    name: String,
    qualityRating: Int,
    averagePrice: Double,
    averageDeliveryTime: Int,
    averageOrderQuantity: Int,
    isCertified: Boolean,
    supplierClassification: SupplierClassification,
    val contactInfo: LocalContactInfo
) : Supplier(name, qualityRating, averagePrice, averageDeliveryTime, averageOrderQuantity, isCertified, supplierClassification, SupplierType.LOCAL) {
    override fun getSupplierType(): String = "Local Supplier"
}
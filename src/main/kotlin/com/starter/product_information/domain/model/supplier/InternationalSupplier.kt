package com.starter.product_information.domain.model.supplier

import com.starter.product_information.domain.model.contactInformation.InternationalContactInfo

class InternationalSupplier(
    name: String,
    qualityRating: Int,
    averagePrice: Double,
    averageDeliveryTime: Int,
    averageOrderQuantity: Int,
    isCertified: Boolean,
    supplierClassification: SupplierClassification,
    val contactInfo: InternationalContactInfo,
    val countryOfOrigin: String
) : Supplier(name, qualityRating, averagePrice, averageDeliveryTime, averageOrderQuantity, isCertified, supplierClassification, SupplierType.INTERNATIONAL) {
    override fun getSupplierType(): String = "International Supplier"
}
package com.starter.product_information.domain.model.manufacturer

class OEMManufacturer(
    name: String,
    country: String,
    val oemCode: String
) : Manufacturer(name, country, ManufacturerType.OEM) {
    override fun getManufacturerType(): String = "OEM Manufacturer"
}
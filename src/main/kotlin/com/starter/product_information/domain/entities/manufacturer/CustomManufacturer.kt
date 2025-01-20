package com.starter.product_information.domain.entities.manufacturer

class CustomManufacturer(
    name: String,
    country: String,
    val customizationOptions: List<String>
) : Manufacturer(name, country, ManufacturerType.CUSTOM) {
    override fun getManufacturerType(): String = "Custom Manufacturer"
}
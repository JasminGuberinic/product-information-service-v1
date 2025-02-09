package com.starter.product_information.domain.model.manufacturer

abstract class Manufacturer(
    val name: String,
    val country: String,
    val manufacturerType: ManufacturerType
) {
    abstract fun getManufacturerType(): String
}
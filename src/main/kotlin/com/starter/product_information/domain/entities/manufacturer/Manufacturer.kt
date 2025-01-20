package com.starter.product_information.domain.entities.manufacturer

abstract class Manufacturer(
    val name: String,
    val country: String,
    val manufacturerType: ManufacturerType
) {
    abstract fun getManufacturerType(): String
}
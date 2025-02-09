package com.starter.product_information.domain.model.contactInformation

data class InternationalContactInfo(
    val phoneNumber: String,
    val email: String,
    val internationalAddress: String,
    val timeZone: String
)
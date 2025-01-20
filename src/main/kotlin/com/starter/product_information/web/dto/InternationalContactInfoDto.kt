package com.starter.product_information.web.dto

import com.starter.product_information.domain.entities.contactInformation.InternationalContactInfo

data class InternationalContactInfoDto(
    val phoneNumber: String,
    val email: String,
    val internationalAddress: String,
    val timeZone: String,
    val countryOfOrigin: String
) {
    fun toDomain(): InternationalContactInfo {
        return InternationalContactInfo(
            phoneNumber = this.phoneNumber,
            email = this.email,
            internationalAddress = this.internationalAddress,
            timeZone = this.timeZone
        )
    }
}
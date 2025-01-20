package com.starter.product_information.web.dto

import com.starter.product_information.domain.entities.contactInformation.LocalContactInfo

data class LocalContactInfoDto(
    val phoneNumber: String,
    val email: String,
    val address: String
) {
    fun toDomain(): LocalContactInfo {
        return LocalContactInfo(
            phoneNumber = this.phoneNumber,
            email = this.email,
            address = this.address
        )
    }
}

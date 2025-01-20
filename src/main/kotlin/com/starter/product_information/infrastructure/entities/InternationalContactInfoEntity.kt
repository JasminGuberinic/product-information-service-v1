package com.starter.product_information.infrastructure.entities

import jakarta.persistence.*

@Entity
@Table(name = "international_contact_info")
data class InternationalContactInfoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val phoneNumber: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val internationalAddress: String,

    @Column(nullable = false)
    val timeZone: String
)

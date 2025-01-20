package com.starter.product_information.infrastructure.entities

import jakarta.persistence.*

@Entity
@Table(name = "local_contact_info")
data class LocalContactInfoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val phoneNumber: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val address: String
)
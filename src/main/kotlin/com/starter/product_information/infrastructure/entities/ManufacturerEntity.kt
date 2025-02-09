package com.starter.product_information.infrastructure.entities

import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import jakarta.persistence.*

@Entity
@Table(name = "manufacturers")
data class ManufacturerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val country: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val manufacturerType: ManufacturerType,

    @Column(nullable = true)
    val oemCode: String? = null, // Specific to OEMManufacturer

    @ElementCollection
    @CollectionTable(name = "customization_options", joinColumns = [JoinColumn(name = "manufacturer_id")])
    @Column(name = "option")
    val customizationOptions: List<String>? = null // Specific to CustomManufacturer
)
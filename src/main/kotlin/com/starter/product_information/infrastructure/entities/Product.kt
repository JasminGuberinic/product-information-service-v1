package com.starter.product_information.infrastructure.entities

import com.starter.product_information.domain.model.product.DistributionChannel
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val price: Double,

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    val supplier: SupplierEntity,

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    val manufacturer: ManufacturerEntity,

    @Column(nullable = false)
    val design: String,

    @Column(nullable = false)
    val customization: Boolean,

    @Column(nullable = false)
    val productionQuantity: Int,

    @Column(nullable = false)
    val brand: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val distributionChannel: DistributionChannel,

    @Column(nullable = false)
    val countryOfOrigin: String,

    @ElementCollection
    @CollectionTable(name = "product_certificates", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "certificate")
    val certificates: List<String>,

    @Column(nullable = false)
    val deliveryTime: Int,

    @Column(nullable = true)
    val companyCountry: String? = null
)
package com.starter.product_information.infrastructure.entities

import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import jakarta.persistence.*

@Entity
@Table(name = "suppliers")
data class SupplierEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val supplierType: SupplierType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val supplierClassification: SupplierClassification,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "local_contact_info_id", referencedColumnName = "id")
    var localContactInfo: LocalContactInfoEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "international_contact_info_id", referencedColumnName = "id")
    var internationalContactInfo: InternationalContactInfoEntity? = null,

    @Column(nullable = true)
    var countryOfOrigin: String? = null,

    @Column(nullable = false)
    val qualityRating: Int,

    @Column(nullable = false)
    val averagePrice: Double,

    @Column(nullable = false)
    val averageDeliveryTime: Int,

    @Column(nullable = false)
    val averageOrderQuantity: Int,

    @Column(nullable = false)
    val isCertified: Boolean
)
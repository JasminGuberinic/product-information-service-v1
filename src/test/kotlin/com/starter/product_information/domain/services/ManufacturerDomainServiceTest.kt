package com.starter.product_information.domain.services

import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.Manufacturer
import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.Supplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ManufacturerDomainServiceTest {

    private lateinit var manufacturerDomainService: ManufacturerDomainService
    private lateinit var defaultSupplier: Supplier
    private lateinit var defaultManufacturer: Manufacturer

    @BeforeEach
    fun setup() {
        manufacturerDomainService = ManufacturerDomainService()

        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        defaultSupplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        defaultManufacturer = OEMManufacturer(
            name = "Test Manufacturer",
            country = "USA",
            oemCode = "TEST123"
        )
    }

    @Test
    @DisplayName("Should return OEM when product meets OEM criteria")
    fun `determineManufacturerType returns OEM for products meeting OEM criteria`() {
        // Given
        val product = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = "original",
            customization = false,
            productionQuantity = 1500,
            brand = "standard",
            distributionChannel = DistributionChannel.ONLINE,
            price = 500.0,
            countryOfOrigin = "USA",
            certificates = listOf("ISO9001"),
            deliveryTime = 30,
            companyCountry = "USA"
        )

        // When
        val result = manufacturerDomainService.determineManufacturerType(product)

        // Then
        assertEquals(ManufacturerType.OEM, result)
    }

    @Test
    @DisplayName("Should return CUSTOM_HIGH_END when product meets luxury criteria")
    fun `determineManufacturerType returns CUSTOM_HIGH_END for luxury products`() {
        // Given
        val product = Product(
            id = 2L,
            name = "Luxury Product",
            description = "Luxury Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = "custom",
            customization = true,
            productionQuantity = 100,
            brand = "luxury",
            distributionChannel = DistributionChannel.DIRECT,
            price = 15000.0,
            countryOfOrigin = "Italy",
            certificates = listOf("ISO9001", "Luxury Certification"),
            deliveryTime = 60,
            companyCountry = "Italy"
        )

        // When
        val result = manufacturerDomainService.determineManufacturerType(product)

        // Then
        assertEquals(ManufacturerType.CUSTOM_HIGH_END, result)
    }

    @Test
    @DisplayName("Should return CUSTOM as default when no specific criteria is met")
    fun `determineManufacturerType returns CUSTOM as default`() {
        // Given
        val product = Product(
            id = 3L,
            name = "Regular Product",
            description = "Regular Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = "standard",
            customization = false,
            productionQuantity = 500,
            brand = "regular",
            distributionChannel = DistributionChannel.ONLINE,
            price = 5000.0,
            countryOfOrigin = "China",
            certificates = listOf("Basic Cert"),
            deliveryTime = 15,
            companyCountry = "China"
        )

        // When
        val result = manufacturerDomainService.determineManufacturerType(product)

        // Then
        assertEquals(ManufacturerType.CUSTOM, result)
    }

    @Test
    @DisplayName("Should return CUSTOM when product has original design but low production quantity")
    fun `determineManufacturerType returns CUSTOM for original design with low production`() {
        // Given
        val product = Product(
            id = 4L,
            name = "Small Batch Product",
            description = "Small Batch Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = "original",
            customization = false,
            productionQuantity = 500, // Below OEM threshold
            brand = "standard",
            distributionChannel = DistributionChannel.ONLINE,
            price = 800.0,
            countryOfOrigin = "Japan",
            certificates = listOf("ISO9001"),
            deliveryTime = 20,
            companyCountry = "Japan"
        )

        // When
        val result = manufacturerDomainService.determineManufacturerType(product)

        // Then
        assertEquals(ManufacturerType.CUSTOM, result)
    }

    @Test
    @DisplayName("Should return CUSTOM when product has high price but wrong distribution channel")
    fun `determineManufacturerType returns CUSTOM for expensive product with wrong channel`() {
        // Given
        val product = Product(
            id = 5L,
            name = "Expensive Online Product",
            description = "Expensive Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = "custom",
            customization = true,
            productionQuantity = 100,
            brand = "premium",
            distributionChannel = DistributionChannel.ONLINE, // Not DIRECT
            price = 12000.0,
            countryOfOrigin = "Switzerland",
            certificates = listOf("Premium Cert"),
            deliveryTime = 45,
            companyCountry = "Switzerland"
        )

        // When
        val result = manufacturerDomainService.determineManufacturerType(product)

        // Then
        assertEquals(ManufacturerType.CUSTOM, result)
    }
}
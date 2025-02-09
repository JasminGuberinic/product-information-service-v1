package com.starter.product_information.domain.services

import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import kotlin.test.assertEquals

class SupplierDomainServiceTest {

    private lateinit var supplierDomainService: SupplierDomainService
    private lateinit var defaultProduct: Product
    private lateinit var defaultManufacturer: OEMManufacturer

    @BeforeEach
    fun setup() {
        supplierDomainService = SupplierDomainService()

        defaultManufacturer = OEMManufacturer(
            name = "Test Manufacturer",
            country = "USA",
            oemCode = "TEST123"
        )
    }

    @Test
    @DisplayName("Should determine INTERNATIONAL supplier type for Chinese products with high quantity")
    fun `determineSupplierType returns INTERNATIONAL for Chinese high quantity products`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        val product = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = defaultManufacturer,
            design = "standard",
            customization = false,
            productionQuantity = 15000, // > 10000
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 99.99,
            countryOfOrigin = "China",
            certificates = listOf(),
            deliveryTime = 30,
            companyCountry = "USA"
        )

        // When
        val result = supplierDomainService.determineSupplierType(product)

        // Then
        assertEquals(SupplierType.INTERNATIONAL, result)
    }

    @Test
    @DisplayName("Should determine INTERNATIONAL supplier type for expensive certified products")
    fun `determineSupplierType returns INTERNATIONAL for expensive certified products`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        val product = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = defaultManufacturer,
            design = "standard",
            customization = false,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 6000.0, // > 5000
            countryOfOrigin = "Germany",
            certificates = listOf("ISO9001"),
            deliveryTime = 30,
            companyCountry = "USA"
        )

        // When
        val result = supplierDomainService.determineSupplierType(product)

        // Then
        assertEquals(SupplierType.INTERNATIONAL, result)
    }

    @Test
    @DisplayName("Should determine LOCAL supplier type for quick local delivery")
    fun `determineSupplierType returns LOCAL for quick local delivery`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        val product = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = defaultManufacturer,
            design = "standard",
            customization = false,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 99.99,
            countryOfOrigin = "USA",
            certificates = listOf(),
            deliveryTime = 5, // < 7
            companyCountry = "USA" // Same as countryOfOrigin
        )

        // When
        val result = supplierDomainService.determineSupplierType(product)

        // Then
        assertEquals(SupplierType.LOCAL, result)
    }

    @Test
    @DisplayName("Should determine UNKNOWN supplier type when no criteria is met")
    fun `determineSupplierType returns UNKNOWN when no criteria is met`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        val product = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = defaultManufacturer,
            design = "standard",
            customization = false,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 99.99,
            countryOfOrigin = "Germany",
            certificates = listOf(),
            deliveryTime = 10,
            companyCountry = "USA"
        )

        // When
        val result = supplierDomainService.determineSupplierType(product)

        // Then
        assertEquals(SupplierType.UNKNOWN, result)
    }

    @Test
    @DisplayName("Should classify supplier as Premium based on high ratings and metrics")
    fun `classifySupplier returns Premium for high performing suppliers`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Premium Supplier",
            qualityRating = 5,
            averagePrice = 150.0, // > 100
            averageDeliveryTime = 5, // < 10
            averageOrderQuantity = 1500, // > 1000
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        // When
        val result = supplierDomainService.classifySupplier(supplier)

        // Then
        assertEquals(SupplierClassification.Premium, result)
    }

    @Test
    @DisplayName("Should classify supplier as Standard based on average metrics")
    fun `classifySupplier returns Standard for average performing suppliers`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Standard Supplier",
            qualityRating = 3,
            averagePrice = 80.0,
            averageDeliveryTime = 15,
            averageOrderQuantity = 800,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        // When
        val result = supplierDomainService.classifySupplier(supplier)

        // Then
        assertEquals(SupplierClassification.Standard, result)
    }

    @Test
    @DisplayName("Should classify supplier as LowCost based on price and quantity metrics")
    fun `classifySupplier returns LowCost for high volume low price suppliers`() {
        // Given
        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Budget Supplier",
            qualityRating = 3,
            averagePrice = 40.0, // < 50
            averageDeliveryTime = 25, // > 20
            averageOrderQuantity = 6000, // > 5000
            isCertified = false,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        // When
        val result = supplierDomainService.classifySupplier(supplier)

        // Then
        assertEquals(SupplierClassification.LowCost, result)
    }
}
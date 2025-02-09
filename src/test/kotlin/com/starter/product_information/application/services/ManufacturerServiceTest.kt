package com.starter.product_information.application.service

import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.CustomManufacturer
import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.services.ManufacturerDomainService
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.repositories.ManufacturerRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ManufacturerServiceTest {

    private lateinit var manufacturerService: ManufacturerService

    @Mock
    private lateinit var manufacturerRepository: ManufacturerRepository

    @Mock
    private lateinit var manufacturerDomainService: ManufacturerDomainService

    private lateinit var testProduct: Product

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        manufacturerService = ManufacturerService(manufacturerRepository, manufacturerDomainService)

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

        testProduct = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = OEMManufacturer(
                name = "Test Manufacturer",
                country = "USA",
                oemCode = "TEST123"
            ),
            design = "original",
            customization = false,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 99.99,
            countryOfOrigin = "USA",
            certificates = listOf("ISO9001"),
            deliveryTime = 30,
            companyCountry = "USA"
        )
    }

    @Test
    @DisplayName("Should create OEM manufacturer when type is OEM")
    fun `createManufacturer creates OEM manufacturer successfully`() {
        // Given
        val manufacturerType = ManufacturerType.OEM
        val expectedEntity = ManufacturerEntity(
            name = "Test Manufacturer",
            country = "USA",
            manufacturerType = manufacturerType,
            oemCode = "TEST123"
        )

        `when`(manufacturerDomainService.determineManufacturerType(testProduct)).thenReturn(manufacturerType)
        `when`(manufacturerRepository.save(any(ManufacturerEntity::class.java))).thenReturn(expectedEntity)

        // When
        val result = manufacturerService.createManufacturer(testProduct)

        // Then
        assertEquals(expectedEntity.name, result.name)
        assertEquals(expectedEntity.country, result.country)
        assertEquals(expectedEntity.manufacturerType, result.manufacturerType)
        assertEquals(expectedEntity.oemCode, result.oemCode)
        verify(manufacturerRepository).save(any(ManufacturerEntity::class.java))
    }

    @Test
    @DisplayName("Should create CUSTOM manufacturer when type is CUSTOM")
    fun `createManufacturer creates CUSTOM manufacturer successfully`() {
        // Given
        val manufacturerType = ManufacturerType.CUSTOM
        val customManufacturer = CustomManufacturer(
            name = "Custom Manufacturer",
            country = "Germany",
            customizationOptions = listOf("Option1", "Option2")
        )

        val customProduct = Product(
            id = testProduct.id,
            name = testProduct.name,
            description = testProduct.description,
            supplier = testProduct.supplier,
            manufacturer = customManufacturer,
            design = testProduct.design,
            customization = testProduct.customization,
            productionQuantity = testProduct.productionQuantity,
            brand = testProduct.brand,
            distributionChannel = testProduct.distributionChannel,
            price = testProduct.price,
            countryOfOrigin = testProduct.countryOfOrigin,
            certificates = testProduct.certificates,
            deliveryTime = testProduct.deliveryTime,
            companyCountry = testProduct.companyCountry
        )

        val expectedEntity = ManufacturerEntity(
            name = "Custom Manufacturer",
            country = "Germany",
            manufacturerType = manufacturerType,
            customizationOptions = listOf("Option1", "Option2")
        )

        `when`(manufacturerDomainService.determineManufacturerType(customProduct)).thenReturn(manufacturerType)
        `when`(manufacturerRepository.save(any(ManufacturerEntity::class.java))).thenReturn(expectedEntity)

        // When
        val result = manufacturerService.createManufacturer(customProduct)

        // Then
        assertEquals(expectedEntity.name, result.name)
        assertEquals(expectedEntity.country, result.country)
        assertEquals(expectedEntity.manufacturerType, result.manufacturerType)
        assertEquals(expectedEntity.customizationOptions, result.customizationOptions)
        verify(manufacturerRepository).save(any(ManufacturerEntity::class.java))
    }

    @Test
    @DisplayName("Should find manufacturer by name and country")
    fun `findManufacturerByNameAndCountry returns manufacturer when exists`() {
        // Given
        val name = "Test Manufacturer"
        val country = "USA"
        val expectedEntity = ManufacturerEntity(
            name = name,
            country = country,
            manufacturerType = ManufacturerType.OEM,
            oemCode = "TEST123"
        )

        `when`(manufacturerRepository.findByNameAndCountry(name, country)).thenReturn(expectedEntity)

        // When
        val result = manufacturerService.findManufacturerByNameAndCountry(name, country)

        // Then
        assertEquals(expectedEntity, result)
        verify(manufacturerRepository).findByNameAndCountry(name, country)
    }

    @Test
    @DisplayName("Should return null when manufacturer not found")
    fun `findManufacturerByNameAndCountry returns null when manufacturer not found`() {
        // Given
        val name = "Nonexistent Manufacturer"
        val country = "Nowhere"

        `when`(manufacturerRepository.findByNameAndCountry(name, country)).thenReturn(null)

        // When
        val result = manufacturerService.findManufacturerByNameAndCountry(name, country)

        // Then
        assertNull(result)
        verify(manufacturerRepository).findByNameAndCountry(name, country)
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unsupported manufacturer type")
    fun `createManufacturer throws exception for unsupported manufacturer type`() {
        // Given
        `when`(manufacturerDomainService.determineManufacturerType(testProduct)).thenReturn(ManufacturerType.UNKNOWN)

        // When/Then
        assertThrows<IllegalArgumentException> {
            manufacturerService.createManufacturer(testProduct)
        }

        verify(manufacturerDomainService).determineManufacturerType(testProduct)
    }
}